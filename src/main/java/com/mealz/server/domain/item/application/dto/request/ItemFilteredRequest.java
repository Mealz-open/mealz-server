package com.mealz.server.domain.item.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mealz.server.domain.item.core.constant.ItemSortField;
import com.mealz.server.domain.shop.core.constant.ShopCategory;
import com.mealz.server.global.util.PageableConstants;
import com.mealz.server.global.util.PageableUtil;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ItemFilteredRequest {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate date;

  private ShopCategory shopCategory;

  private int pageNumber;

  private int pageSize;

  private ItemSortField sortField;

  private Sort.Direction sortDirection;

  public ItemFilteredRequest() {
    this.pageNumber = 1;
    this.pageSize = PageableConstants.DEFAULT_PAGE_SIZE;
    this.sortField = ItemSortField.CREATED_DATE;
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
