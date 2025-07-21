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

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final UserService userService;

    public String initBalance(long userId) {
        userService.validateUser(userId);

        if (balanceRepository.existsById(userId))
            throw new BalanceException("Balance has already been created for this user id: " + userId);

        var balance = Balance.builder()
                .userId(userId)
                .balance(BigDecimal.ZERO)
                .updatedAt(LocalDateTime.now())
                .build();
        balanceRepository.save(balance);

        return "Balance created";
    }

    public BalanceResponse getUserBalance(long userId) {
        userService.validateUser(userId);

        var balance = balanceRepository.findBalanceByUserId(userId)
                .orElseThrow(() -> new BalanceException("User balance not found"));

        return new BalanceResponse(
                balance.getUserId(),
                balance.getBalance(),
                balance.getUpdatedAt()
        );
    }

    public String changeBalance(BalanceChangeRequest request) {
        userService.validateUser(request.getUserId());

        var balance = balanceRepository.findBalanceByUserId(request.getUserId())
                .orElseThrow(() -> new BalanceException("User balance not found"));

        if (request.getType() == ChangeType.INCREASE) {

            balance.setBalance(balance.getBalance().add(request.getAmount()));

        } else if (request.getType() == ChangeType.DECREASE) {

            if (balance.getBalance().compareTo(request.getAmount()) < 0)
                throw new BalanceException("Insufficient balance");

            balance.setBalance(balance.getBalance().subtract(request.getAmount()));
        }

        balance.setUpdatedAt(LocalDateTime.now());
        balanceRepository.save(balance);

        return "Balance updated";
    }
}
