package com.mealz.server.domain.shop.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopCategory {
  KOREAN("한식"),
  CHINESE("중식"),
  JAPANESE("일식"),
  WESTERN("양식"),
  ASIAN("아시안"),
  SNACK("분식"),
  FAST_FOOD("패스트푸드"),
  DESSERT("디저트"),
  BEVERAGE("음료"),
  SIDE_DISH("반찬"),
  ETC("기타");

  private final String displayName;
}
