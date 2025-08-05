package com.mealz.server.domain.shop.infrastructure.repository;

import com.mealz.server.domain.shop.core.constant.ShopSortField;
import com.mealz.server.domain.shop.infrastructure.entity.QShop;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import com.mealz.server.global.util.QueryDslUtil;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom {

  private static final QShop SHOP = QShop.shop;

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Shop> filteredShopByDistance(
      double longitude,
      double latitude,
      double radiusInMeters,
      Pageable pageable
  ) {

    ComparableExpression<Double> distanceExpression = Expressions.comparableTemplate(
        Double.class,
        "ST_Distance({0}, ST_SetSRID(ST_MakePoint({1}, {2}), 4326))",
        SHOP.geom,
        longitude,
        latitude
    );

    // ST_DWithin을 통한 반경 내 매장 필터링
    JPAQuery<Shop> contentQuery = queryFactory
        .selectFrom(SHOP)
        .where(distanceExpression.loe(radiusInMeters));

    // enum.property -> 표현식 매핑
    Map<String, ComparableExpression<?>> customSortMap = Collections.singletonMap(
        ShopSortField.NEAREST.getProperty(),
        distanceExpression
    );

    // applySorting 동적 정렬
    QueryDslUtil.applySorting(
        contentQuery,
        pageable,
        Shop.class,
        SHOP.getMetadata().getName(),
        customSortMap
    );

    // countQuery 생성
    JPAQuery<Long> countQuery = queryFactory
        .select(SHOP.count())
        .from(SHOP)
        .where(distanceExpression.loe(radiusInMeters));

    return QueryDslUtil.fetchPage(contentQuery, countQuery, pageable);
  }
}
