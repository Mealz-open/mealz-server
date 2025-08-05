package com.mealz.server.domain.shop.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.shop.core.constant.ShopCategory;
import com.mealz.server.global.persistence.BasePostgresEntity;
import jakarta.persistence.Column;
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
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Shop extends BasePostgresEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false, unique = true)
  private UUID shopId;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @Column(nullable = false)
  private String shopName;

  @Enumerated(EnumType.STRING)
  private ShopCategory shopCategory;

  private String shopImageUrl;

  @Column(columnDefinition = "geography(Point, 4326)", nullable = false)
  private Point<G2D> geom;

  @Column(nullable = false)
  private String siDo;

  @Column(nullable = false)
  private String siGunGu;

  @Column(nullable = false)
  private String eupMyoenDong;

  private String ri;
}
