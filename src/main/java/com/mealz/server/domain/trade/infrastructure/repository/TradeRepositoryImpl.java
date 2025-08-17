package com.mealz.server.domain.trade.infrastructure.repository;

import com.mealz.server.domain.item.infrastructure.entity.QItem;
import com.mealz.server.domain.member.infrastructure.entity.QMember;
import com.mealz.server.domain.shop.infrastructure.entity.QShop;
import com.mealz.server.domain.trade.core.constant.TradeStatus;
import com.mealz.server.domain.trade.infrastructure.entity.QTrade;
import com.mealz.server.domain.trade.infrastructure.entity.Trade;
import com.mealz.server.global.util.QueryDslUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TradeRepositoryImpl implements TradeRepositoryCustom{

  private static final QTrade TRADE = QTrade.trade;
  private static final QItem ITEM = QItem.item;
  private static final QShop SHOP = QShop.shop;
  private static final QMember DONATOR = new QMember("donator");
  private static final QMember BENEFICIARY = new QMember("beneficiary");

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Trade> filteredTrade(UUID donatorId, UUID beneficiaryId, UUID shopId, UUID itemId, LocalDate date, TradeStatus tradeStatus, Pageable pageable) {
    // 날짜 범위 계산
    LocalDateTime startOfDay = date != null ? date.atStartOfDay() : null;
    LocalDateTime endOfDay = date != null ? date.plusDays(1).atStartOfDay().minusNanos(1) : null;

    BooleanExpression whereClause = QueryDslUtil.allOf(
        QueryDslUtil.eqIfNotNull(DONATOR.memberId, donatorId),
        QueryDslUtil.eqIfNotNull(BENEFICIARY.memberId, beneficiaryId),
        QueryDslUtil.eqIfNotNull(SHOP.shopId, shopId),
        QueryDslUtil.eqIfNotNull(ITEM.itemId, itemId),
        (startOfDay != null && endOfDay != null)
            ? TRADE.item.pickupStartTime.between(startOfDay, endOfDay)
            : null,
        QueryDslUtil.eqIfNotNull(TRADE.tradeStatus, tradeStatus)
    );

    // contentQuery 생성
    JPAQuery<Trade> contentQuery = queryFactory
        .selectFrom(TRADE)
        .join(TRADE.item, ITEM).fetchJoin()
        .join(ITEM.shop, SHOP).fetchJoin()
        .join(SHOP.member, DONATOR).fetchJoin()
        .join(TRADE.beneficiary, BENEFICIARY).fetchJoin()
        .where(whereClause);

    // applySorting 동적 정렬
    QueryDslUtil.applySorting(
        contentQuery,
        pageable,
        Trade.class,
        TRADE.getMetadata().getName()
    );

    // countQuery 생성
    JPAQuery<Long> countQuery = queryFactory
        .select(TRADE.count())
        .from(TRADE)
        .join(TRADE.item, ITEM)
        .join(ITEM.shop, SHOP)
        .join(SHOP.member, DONATOR)
        .join(TRADE.beneficiary, BENEFICIARY)
        .where(whereClause);

    return QueryDslUtil.fetchPage(contentQuery, countQuery, pageable);
  }
}
