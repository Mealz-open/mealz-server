package com.mealz.server.domain.shop.application.dto.response;

import com.mealz.server.domain.shop.core.constant.ShopCategory;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
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
    Double longitude, // 경도
    Double latitude, // 위도
    String siDo,
    String siGunGu,
    String eupMyoenDong,
    String ri
) {

  public static ShopResponse from(Shop shop) {
    return ShopResponse.builder()
        .shopId(shop.getShopId())
        .nickname(shop.getMember().getNickname())
        .profileUrl(shop.getMember().getProfileUrl())
        .shopName(shop.getShopName())
        .shopCategory(shop.getShopCategory())
        .shopImageUrl(shop.getShopImageUrl())
        .longitude(shop.getGeom().getPosition().getLon())
        .latitude(shop.getGeom().getPosition().getLat())
        .siDo(shop.getSiDo())
        .siGunGu(shop.getSiGunGu())
        .eupMyoenDong(shop.getEupMyoenDong())
        .ri(shop.getRi())
        .build();
  }
}
