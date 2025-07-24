package org.cyber_pantera.balance_service.service;

import lombok.RequiredArgsConstructor;
import org.cyber_pantera.balance_service.exception.BalanceException;
import org.cyber_pantera.balance_service.dto.BalanceChangeRequest;
import org.cyber_pantera.balance_service.dto.BalanceResponse;
import org.cyber_pantera.balance_service.dto.ChangeType;
import org.cyber_pantera.balance_service.entity.Balance;
import org.cyber_pantera.balance_service.repository.BalanceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final UserService userService;

    public CompletableFuture<String> initBalance(long userId) {
        var balance = Balance.builder()
                .userId(userId)
                .balance(BigDecimal.ZERO)
                .updatedAt(LocalDateTime.now())
                .build();

        return userService.validateUser(userId)
                .thenCompose(userResponse -> existsByUserId(userResponse.getId())
                        .thenCompose(isExists -> {
                            if (isExists)
                                throw new BalanceException("Balance has already been created for this user id: " + userId);

                            return saveBalance(balance);
                        })
                        .thenApply(savedBalance -> "Balance created for this user: " + userResponse.getEmail())
                );
    }

    public CompletableFuture<BalanceResponse> getUserBalance(long userId) {
        return userService.validateUser(userId)
                .thenCompose(userResponse -> findByUserId(userResponse.getId())
                        .thenApply(balance -> balance.orElseThrow(() ->
                                new BalanceException("User balance not found for this user: " + userResponse.getEmail()))))

                .thenApply(balance -> new BalanceResponse(
                        balance.getUserId(),
                        balance.getBalance(),
                        balance.getUpdatedAt()));
    }

    public CompletableFuture<String> changeBalance(BalanceChangeRequest request) {
        return userService.validateUser(request.getUserId())
                .thenCompose(userResponse -> findByUserId(userResponse.getId())
                        .thenApply(balance -> balance.orElseThrow(() ->
                                new BalanceException("User balance not found for this user: " + userResponse.getEmail()))))
                .thenCompose(balance -> {

                    if (request.getType() == ChangeType.INCREASE)
                        balance.setBalance(balance.getBalance().add(request.getAmount()));

                    else if (request.getType() == ChangeType.DECREASE) {

                        if (balance.getBalance().compareTo(request.getAmount()) < 0)
                            throw new BalanceException("Insufficient balance");

                        balance.setBalance(balance.getBalance().subtract(request.getAmount()));
                    }

                    balance.setUpdatedAt(LocalDateTime.now());
                    return saveBalance(balance);
                })
                .thenApply(balance -> "Balance updated");
    }

    private CompletableFuture<Balance> saveBalance(Balance balance) {
        return CompletableFuture.supplyAsync(() -> balanceRepository.save(balance));
    }

    private CompletableFuture<Boolean> existsByUserId(long userId) {
        return CompletableFuture.supplyAsync(() -> balanceRepository.existsById(userId));
    }

    private CompletableFuture<Optional<Balance>> findByUserId(long userId) {
        return CompletableFuture.supplyAsync(() -> balanceRepository.findByUserId(userId));
    }
}
