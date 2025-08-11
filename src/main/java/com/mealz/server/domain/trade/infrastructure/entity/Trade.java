package com.mealz.server.domain.trade.infrastructure.entity;

import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.trade.core.constant.TradeStatus;
import com.mealz.server.global.persistence.BasePostgresEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Trade extends BasePostgresEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID tradeId;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member beneficiary;

  @ManyToOne(fetch = FetchType.LAZY)
  private Item item;

  @Enumerated(EnumType.STRING)
  private TradeStatus tradeStatus;

  private int quantity;
}
