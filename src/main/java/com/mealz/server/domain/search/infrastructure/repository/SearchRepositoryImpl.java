package com.mealz.server.domain.search.infrastructure.repository;

import com.mealz.server.domain.item.core.constant.ItemSortField;
import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.item.infrastructure.entity.QItem;
import com.mealz.server.domain.shop.core.constant.ShopSortField;
import com.mealz.server.domain.shop.infrastructure.entity.QShop;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import com.mealz.server.global.util.QueryDslUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
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
public class SearchRepositoryImpl implements SearchRepositoryCustom {

  private static final QShop SHOP = QShop.shop;
  private static final QItem ITEM = QItem.item;

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Shop> searchShop(String keyword, Double latitude, Double longitude, Pageable pageable) {
    BooleanExpression whereClause = QueryDslUtil.anyOf(
        QueryDslUtil.likeIgnoreCase(SHOP.shopName, keyword),
        QueryDslUtil.likeIgnoreCase(SHOP.shopDescription, keyword),
        QueryDslUtil.likeIgnoreCase(SHOP.siDo, keyword),
        QueryDslUtil.likeIgnoreCase(SHOP.siGunGu, keyword),
        QueryDslUtil.likeIgnoreCase(SHOP.eupMyoenDong, keyword)
    );

    ComparableExpression<Double> distanceExpression = Expressions.comparableTemplate(
        Double.class,
        "ST_Distance({0}, ST_SetSRID(ST_MakePoint({1}, {2}), 4326))",
        SHOP.geom,
        longitude,
        latitude
    );

    JPAQuery<Shop> contentQuery = queryFactory
        .selectFrom(SHOP)
        .where(whereClause);

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
        .where(whereClause);

    return QueryDslUtil.fetchPage(contentQuery, countQuery, pageable);
  }

  @Override
  public Page<Item> searchItem(String keyword, Pageable pageable) {

    BooleanExpression whereClause = QueryDslUtil.anyOf(
        QueryDslUtil.likeIgnoreCase(ITEM.itemName, keyword),
        QueryDslUtil.likeIgnoreCase(SHOP.shopName, keyword)
    );

    Map<String, ComparableExpression<?>> customSortMap = Collections.singletonMap(
        ItemSortField.PICKUP_TIME.getProperty(),
        ITEM.pickupStartTime
    );

    // contentQuery 생성
    JPAQuery<Item> contentQuery = queryFactory
        .selectFrom(ITEM)
        .join(ITEM.shop, SHOP).fetchJoin()
        .where(whereClause);

    // applySorting 동적 정렬
    QueryDslUtil.applySorting(
        contentQuery,
        pageable,
        Item.class,
        ITEM.getMetadata().getName(),
        customSortMap
    );

    // countQuery 생성
    JPAQuery<Long> countQuery = queryFactory
        .select(ITEM.count())
        .from(ITEM)
        .where(whereClause);

    return QueryDslUtil.fetchPage(contentQuery, countQuery, pageable);
  }
}
