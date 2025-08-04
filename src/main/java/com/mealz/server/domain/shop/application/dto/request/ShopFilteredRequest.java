package com.mealz.server.domain.shop.application.dto.request;

import com.mealz.server.domain.shop.core.constant.ShopSortField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopFilteredRequest {

  private ShopSortField sortField;

  private Sort.Direction sortDirection;
}
