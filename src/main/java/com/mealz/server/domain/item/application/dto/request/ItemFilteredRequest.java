package com.mealz.server.domain.item.application.dto.request;

import com.mealz.server.domain.shop.core.constant.ShopCategory;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemFilteredRequest {

  private String itemName;

  private ShopCategory shopCategory;

  private LocalDate startDate;

  private LocalDate endDate;

  private Double longitude;

  private Double latitude;

  private Integer radiusInMeters;
}
