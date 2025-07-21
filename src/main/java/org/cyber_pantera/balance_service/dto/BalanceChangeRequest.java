package org.cyber_pantera.balance_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceChangeRequest {

    private long userId;
    private BigDecimal amount;
    private ChangeType type;
}
