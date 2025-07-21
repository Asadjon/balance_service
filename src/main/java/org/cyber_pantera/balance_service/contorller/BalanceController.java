package org.cyber_pantera.balance_service.contorller;

import lombok.RequiredArgsConstructor;
import org.cyber_pantera.balance_service.dto.BalanceChangeRequest;
import org.cyber_pantera.balance_service.dto.BalanceResponse;
import org.cyber_pantera.balance_service.service.BalanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/{userId}")
    public ResponseEntity<BalanceResponse> getMyBalance(@PathVariable long userId) {
        return ResponseEntity.ok(balanceService.getUserBalance(userId));
    }

    @PostMapping("/change")
    public ResponseEntity<String> changeBalance(@RequestBody BalanceChangeRequest request) {
        return ResponseEntity.ok(balanceService.changeBalance(request));
    }

    @PostMapping("/init")
    public ResponseEntity<String> initBalance(@RequestParam long userId) {
        return ResponseEntity.ok(balanceService.initBalance(userId));
    }
}
