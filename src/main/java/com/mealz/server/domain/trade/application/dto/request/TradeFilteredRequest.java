package com.mealz.server.domain.trade.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mealz.server.domain.trade.core.constant.TradeSortField;
import com.mealz.server.domain.trade.core.constant.TradeStatus;
import com.mealz.server.global.util.PageableConstants;
import com.mealz.server.global.util.PageableUtil;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TradeFilteredRequest {

  UUID donatorId;

  UUID beneficiaryId;

  UUID shopId;

  UUID itemId;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate date;

  private TradeStatus tradeStatus;

  private int pageNumber;

  private int pageSize;

  private TradeSortField sortField;

  private Sort.Direction sortDirection;

  public TradeFilteredRequest() {
    this.pageNumber = 1;
    this.pageSize = PageableConstants.DEFAULT_PAGE_SIZE;
    this.sortField = TradeSortField.CREATED_DATE;
    this.sortDirection = Direction.DESC;
  }

  public Pageable toPageable() {
    return PageableUtil.createPageable(
        pageNumber,
        pageSize,
        PageableConstants.DEFAULT_PAGE_SIZE,
        sortField,
        sortDirection
    );
  }
}
