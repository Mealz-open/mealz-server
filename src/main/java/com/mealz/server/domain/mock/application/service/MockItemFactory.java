package com.mealz.server.domain.mock.application.service;

import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.item.infrastructure.entity.ItemImage;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MockItemFactory {

  private final Faker koFaker;
  private final Faker enFaker;

  /**
   * 단일 Item 목데이터 생성
   */
  public Item generateItem(Shop shop) {
    String itemName = koFaker.food().ingredient() + " " + koFaker.commerce().productName() + koFaker.random().nextInt(100);
    int quantity = koFaker.number().numberBetween(1, 10);

    // expiredDate: 오늘부터 1~30일 뒤
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime expiredDate = now.plusDays(koFaker.number().numberBetween(1, 30));

    // pickupStartTime: 오늘부터 0~5일 뒤 사이 랜덤
    LocalDateTime pickupStart = now.plusDays(koFaker.number().numberBetween(0, 5))
        .withHour(koFaker.number().numberBetween(8, 20))
        .withMinute(0)
        .withSecond(0)
        .withNano(0);

    // pickupEndTime: pickupStartTime + (1~4시간)
    LocalDateTime pickupEnd = pickupStart.plusHours(koFaker.number().numberBetween(1, 4));

    return Item.builder()
        .shop(shop)
        .itemName(itemName)
        .quantity(quantity)
        .expiredDate(expiredDate)
        .pickupStartTime(pickupStart)
        .pickupEndTime(pickupEnd)
        .build();
  }

  /**
   * 한 Item 당 1~3개의 ItemImage 목데이터 생성
   */
  public List<ItemImage> generateImages(Item item) {
    int count = koFaker.number().numberBetween(1, 4);
    List<ItemImage> images = new ArrayList<>(count);
    for (int i = 0; i < count; i++) {
      images.add(ItemImage.builder()
          .item(item)
          .itemImageUrl(koFaker.internet().image())
          .build());
    }
    return images;
  }

}
