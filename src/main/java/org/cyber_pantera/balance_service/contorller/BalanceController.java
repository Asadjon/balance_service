package org.cyber_pantera.balance_service.contorller;

import lombok.RequiredArgsConstructor;
import org.cyber_pantera.balance_service.dto.BalanceChangeRequest;
import org.cyber_pantera.balance_service.dto.BalanceResponse;
import org.cyber_pantera.balance_service.service.BalanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/{userId}")
    public CompletableFuture<ResponseEntity<BalanceResponse>> getMyBalance(@PathVariable long userId) {
        return balanceService.getUserBalance(userId)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/change")
    public CompletableFuture<ResponseEntity<String>> changeBalance(@RequestBody BalanceChangeRequest request) {
        return balanceService.changeBalance(request)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/init")
    public CompletableFuture<ResponseEntity<String>> initBalance(@RequestParam long userId) {
        return balanceService.initBalance(userId)
                .thenApply(ResponseEntity::ok);
    }
}
