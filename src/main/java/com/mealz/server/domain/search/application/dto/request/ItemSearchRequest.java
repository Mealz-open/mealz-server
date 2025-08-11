package com.mealz.server.domain.search.application.dto.request;

import com.mealz.server.domain.item.core.constant.ItemSortField;
import com.mealz.server.global.util.PageableConstants;
import com.mealz.server.global.util.PageableUtil;
import jakarta.validation.constraints.NotBlank;
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
public class ItemSearchRequest {

  @NotBlank(message = "검색어를 입력하세요")
  private String keyword;

  private int pageNumber;

  private int pageSize;

  private ItemSortField sortField;

  private Sort.Direction sortDirection;

  public ItemSearchRequest() {
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
