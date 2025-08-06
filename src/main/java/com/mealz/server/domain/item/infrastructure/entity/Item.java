package com.mealz.server.domain.item.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import com.mealz.server.global.persistence.BasePostgresEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
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
public class Item extends BasePostgresEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID itemId;

  @ManyToOne(fetch = FetchType.LAZY)
  private Shop shop;

  @Column(nullable = false)
  private String itemName;

  private int quantity;

  @Column(columnDefinition = "TIMESTAMP(0)")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime expiredDate;

  @Column(columnDefinition = "TIMESTAMP(0)")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime pickupStartTime;

  @Column(columnDefinition = "TIMESTAMP(0)")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime pickupEndTime;
}
