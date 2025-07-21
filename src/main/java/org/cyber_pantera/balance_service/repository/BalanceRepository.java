package org.cyber_pantera.balance_service.repository;

import org.cyber_pantera.balance_service.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findBalanceByUserId(Long userId);
}
