package com.mealz.server.domain.shop.infrastructure.repository;

import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopRepositoryCustom {

  /**
   * 주어진 위치로부터 radiusInMeters 이내 매장 조회
   */
  Page<Shop> filteredShopByDistance(
      double longitude,
      double latitude,
      double radiusInMeters,
      Pageable pageable
  );
}
