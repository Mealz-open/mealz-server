package com.mealz.server.domain.trade.infrastructure.repository;

import com.mealz.server.domain.trade.infrastructure.entity.Trade;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, UUID> {

}
