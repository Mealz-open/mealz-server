package com.mealz.server.domain.shop.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mealz.server.domain.shop.core.constant.ShopCategory;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ShopResponse(
    UUID shopId,
    String nickname,
    String profileUrl,
    String shopName,
    ShopCategory shopCategory,
    String shopImageUrl,
    String shopDescription,
    Double longitude, // 경도
    Double latitude, // 위도
    String siDo,
    String siGunGu,
    String eupMyoenDong,
    String ri,
    String shopPhoneNumber,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    LocalTime openTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    LocalTime closeTime
) {

  public static ShopResponse from(Shop shop) {
    return ShopResponse.builder()
        .shopId(shop.getShopId())
        .nickname(shop.getMember().getNickname())
        .profileUrl(shop.getMember().getProfileUrl())
        .shopName(shop.getShopName())
        .shopCategory(shop.getShopCategory())
        .shopImageUrl(shop.getShopImageUrl())
        .shopDescription(shop.getShopDescription())
        .longitude(shop.getGeom().getPosition().getLon())
        .latitude(shop.getGeom().getPosition().getLat())
        .siDo(shop.getSiDo())
        .siGunGu(shop.getSiGunGu())
        .eupMyoenDong(shop.getEupMyoenDong())
        .ri(shop.getRi())
        .shopPhoneNumber(shop.getShopPhoneNumber())
        .openTime(shop.getOpenTime())
        .closeTime(shop.getCloseTime())
        .build();
  }
}
