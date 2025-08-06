package com.mealz.server.domain.item.infrastructure.repository;

import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.shop.core.constant.ShopCategory;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

  /**
   * 물품 필터링 조회
   *
   * @param date         특정 날짜
   * @param shopCategory 카테고리
   */
  Page<Item> filteredItem(LocalDate date, ShopCategory shopCategory, Pageable pageable);

}
