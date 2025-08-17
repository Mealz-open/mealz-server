package com.mealz.server.domain.trade.infrastructure.repository;

import com.mealz.server.domain.trade.core.constant.TradeStatus;
import com.mealz.server.domain.trade.infrastructure.entity.Trade;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TradeRepositoryCustom {

  /**
   * 거래 필터링 조회
   */
  Page<Trade> filteredTrade(UUID donatorId, UUID beneficiaryId, UUID shopId, UUID itemId, LocalDate date, TradeStatus tradeStatus, Pageable pageable);
}
