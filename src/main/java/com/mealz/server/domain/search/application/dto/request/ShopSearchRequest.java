package com.mealz.server.domain.search.application.dto.request;

import com.mealz.server.domain.shop.core.constant.ShopSortField;
import com.mealz.server.global.util.PageableConstants;
import com.mealz.server.global.util.PageableUtil;
import jakarta.validation.constraints.NotBlank;
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
public class ShopSearchRequest {

  @NotBlank(message = "검색어를 입력하세요")
  private String keyword;

  @NotNull
  private double latitude;

  @NotNull
  private double longitude;

  private int pageNumber;

  private int pageSize;

  private ShopSortField sortField;

  private Sort.Direction sortDirection;

  public ShopSearchRequest() {
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
