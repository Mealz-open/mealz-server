package com.mealz.server.domain.search.infrastructure.repository;

import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchRepositoryCustom {

  /**
   * 매장 검색
   */
  Page<Shop> searchShop(
      String keyword,
      Double latitude,
      Double longitude,
      Pageable pageable
  );

  /**
   * 가게 검색
   */
  Page<Item> searchItem(
      String keyword,
      Pageable pageable
  );
}
