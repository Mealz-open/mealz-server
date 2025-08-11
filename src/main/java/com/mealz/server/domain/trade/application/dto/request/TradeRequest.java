package com.mealz.server.domain.trade.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeRequest {

  @NotNull
  private UUID itemId;

  @Min(value = 1, message = "신청 수량은 1 이상의 정수만 가능합니다")
  private int quantity;
}
