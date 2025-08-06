package com.mealz.server.domain.shop.application.dto.request;

import com.mealz.server.domain.shop.core.constant.ShopSortField;
import com.mealz.server.global.util.PageableConstants;
import com.mealz.server.global.util.PageableUtil;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class ShopDistanceFilteredRequest {

  @NotNull
  private double longitude;

  @NotNull
  private double latitude;

  @Min(value = 0, message = "반경 값은 양수만 입력 가능합니다")
  private double radiusInMeters;

  private int pageNumber;

  private int pageSize;

  private ShopSortField sortField;

  private Sort.Direction sortDirection;

  public ShopDistanceFilteredRequest() {
    this.pageNumber = 1;
    this.pageSize = PageableConstants.DEFAULT_PAGE_SIZE;
    this.sortField = ShopSortField.CREATED_DATE;
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
