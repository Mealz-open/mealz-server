package com.mealz.server.domain.mock.application.service;

import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.shop.core.constant.ShopCategory;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import com.mealz.server.global.util.PostGisUtil;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.suhsaechan.suhnicknamegenerator.core.SuhRandomKit;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MockShopFactory {

  private final Faker koFaker;
  private final Faker enFaker;
  private final SuhRandomKit suhRandomKit;

  public Shop generate(Member member) {
    // 1) 랜덤 카테고리 선택
    ShopCategory[] categories = ShopCategory.values();
    int idx = koFaker.number().numberBetween(0, categories.length);
    ShopCategory category = categories[idx];

    // 2) 위도/경도 (koFaker.address()의 문자열을 파싱)
    double latitude = Double.parseDouble(koFaker.address().latitude());
    double longitude = Double.parseDouble(koFaker.address().longitude());

    // 3) 전화번호: 010-XXXX-XXXX
    String phoneNumber = String.format("010-%04d-%04d",
        koFaker.number().numberBetween(0, 10000),
        koFaker.number().numberBetween(0, 10000)
    );

    // 4) 영업시간: 개점(6~11시), 폐점(18~23시), 분은 0 고정
    int openHour = koFaker.number().numberBetween(6, 12);
    int closeHour = koFaker.number().numberBetween(18, 24);
    LocalTime openTime = LocalTime.of(openHour, 0);
    LocalTime closeTime = LocalTime.of(closeHour, 0);

    // 5) 상호명과 설명
    String shopName = koFaker.company().name() + " " + koFaker.food().dish() + koFaker.random().nextInt(1000);
    String description = enFaker.lorem().sentence(8);

    // 6) 주소 정보
    String siDo = koFaker.address().state();
    String siGunGu = koFaker.address().cityName();
    String eupMyoenDong = koFaker.address().streetAddress();
    String ri = koFaker.address().buildingNumber();

    return Shop.builder()
        .member(member)
        .shopName(shopName)
        .shopCategory(category)
        .shopImageUrl(enFaker.internet().image())
        .shopDescription(description)
        .geom(PostGisUtil.makePoint(longitude, latitude))
        .siDo(siDo)
        .siGunGu(siGunGu)
        .eupMyoenDong(eupMyoenDong)
        .ri(ri)
        .shopPhoneNumber(phoneNumber)
        .openTime(openTime)
        .closeTime(closeTime)
        .build();
  }

}
