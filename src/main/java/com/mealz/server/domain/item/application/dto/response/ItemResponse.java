package com.mealz.server.domain.item.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.item.infrastructure.entity.ItemImage;
import com.mealz.server.domain.shop.core.constant.ShopCategory;
import com.mealz.server.global.util.CommonUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record ItemResponse(
    UUID itemId,
    String itemName,
    List<String> itemImageUrls,
    UUID shopId,
    ShopCategory shopCategory,
    String shopName,
    double latitude,
    double longitude,
    String siDo,
    String siGunGu,
    String eupMyoenDong,
    String ri,
    int quantity,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime expiredDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime pickupStartTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime pickupEndTime,
    boolean pickupToday
) {

  private static final LocalDate TODAY = LocalDate.now(ZoneId.of("Asia/Seoul"));

  public static ItemResponse from(Item item, List<ItemImage> images) {
    List<String> imageUrls = new ArrayList<>();
    if (!CommonUtil.nullOrEmpty(images)) {
      imageUrls = images.stream()
          .map(ItemImage::getItemImageUrl)
          .collect(Collectors.toList());
    }
    return ItemResponse.builder()
        .itemId(item.getItemId())
        .itemName(item.getItemName())
        .itemImageUrls(imageUrls)
        .shopId(item.getShop().getShopId())
        .shopCategory(item.getShop().getShopCategory())
        .shopName(item.getShop().getShopName())
        .latitude(item.getShop().getGeom().getPosition().getLat())
        .longitude(item.getShop().getGeom().getPosition().getLon())
        .siDo(item.getShop().getSiDo())
        .siGunGu(item.getShop().getSiGunGu())
        .eupMyoenDong(item.getShop().getEupMyoenDong())
        .ri(item.getShop().getRi())
        .quantity(item.getQuantity())
        .expiredDate(item.getExpiredDate())
        .pickupStartTime(item.getPickupStartTime())
        .pickupEndTime(item.getPickupEndTime())
        .pickupToday(item.getPickupStartTime().toLocalDate().isEqual(TODAY))
        .build();
  }
}
