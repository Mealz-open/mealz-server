package com.mealz.server.domain.item.infrastructure.repository;

import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.item.infrastructure.entity.QItem;
import com.mealz.server.domain.shop.core.constant.ShopCategory;
import com.mealz.server.domain.shop.infrastructure.entity.QShop;
import com.mealz.server.global.util.QueryDslUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

  private static final QItem ITEM = QItem.item;
  private static final QShop SHOP = QShop.shop;

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Item> filteredItem(LocalDate date, ShopCategory shopCategory, Pageable pageable) {
    // 날짜 범위 계산
    LocalDateTime startOfDay = date != null ? date.atStartOfDay() : null;
    LocalDateTime endOfDay = date != null ? date.plusDays(1).atStartOfDay().minusNanos(1) : null;

    BooleanExpression whereClause = QueryDslUtil.allOf(
        (startOfDay != null && endOfDay != null)
            ? ITEM.pickupStartTime.between(startOfDay, endOfDay)
            : null,
        QueryDslUtil.eqIfNotNull(ITEM.shop.shopCategory, shopCategory)
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
        ITEM.getMetadata().getName()
    );

    // countQuery 생성
    JPAQuery<Long> countQuery = queryFactory
        .select(ITEM.count())
        .from(ITEM)
        .where(whereClause);

    return QueryDslUtil.fetchPage(contentQuery, countQuery, pageable);
  }
}
