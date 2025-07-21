package org.cyber_pantera.balance_service.handler;

import org.cyber_pantera.balance_service.exception.BalanceException;
import org.cyber_pantera.balance_service.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BalanceException.class)
    public ResponseEntity<?> transactionException(BalanceException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> userException(UserException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
