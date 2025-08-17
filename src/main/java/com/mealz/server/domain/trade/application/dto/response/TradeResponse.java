package com.mealz.server.domain.trade.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.item.infrastructure.entity.ItemImage;
import com.mealz.server.domain.shop.core.constant.ShopCategory;
import com.mealz.server.domain.trade.core.constant.TradeStatus;
import com.mealz.server.domain.trade.infrastructure.entity.Trade;
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
public record TradeResponse(
    UUID tradeId,
    TradeStatus tradeStatus,
    int tradeQuantity,
    UUID donatorId,
    String donatorNickname,
    String donatorProfileUrl,
    UUID beneficiaryId,
    String beneficiaryNickname,
    String beneficiaryProfileUrl,
    UUID itemId,
    String itemName,
    List<String> itemImageUrls,
    ShopCategory shopCategory,
    String shopName,
    double latitude,
    double longitude,
    String siDo,
    String siGunGu,
    String eupMyoenDong,
    String ri,
    int remainQuantity,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime expiredDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime pickupStartTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime pickupEndTime,
    boolean pickupToday
) {

  private static final LocalDate TODAY = LocalDate.now(ZoneId.of("Asia/Seoul"));

  public static TradeResponse from(Trade trade, Item item, List<ItemImage> images) {
    List<String> imageUrls = new ArrayList<>();
    if (!CommonUtil.nullOrEmpty(images)) {
      imageUrls = images.stream()
          .map(ItemImage::getItemImageUrl)
          .collect(Collectors.toList());
    }
    return TradeResponse.builder()
        .tradeId(trade.getTradeId())
        .tradeStatus(trade.getTradeStatus())
        .tradeQuantity(trade.getQuantity())
        .donatorId(trade.getItem().getShop().getMember().getMemberId())
        .donatorNickname(trade.getItem().getShop().getMember().getNickname())
        .donatorProfileUrl(trade.getItem().getShop().getMember().getProfileUrl())
        .beneficiaryId(trade.getBeneficiary().getMemberId())
        .beneficiaryNickname(trade.getBeneficiary().getNickname())
        .beneficiaryProfileUrl(trade.getBeneficiary().getProfileUrl())
        .itemId(item.getItemId())
        .itemName(item.getItemName())
        .itemImageUrls(imageUrls)
        .shopCategory(item.getShop().getShopCategory())
        .shopName(item.getShop().getShopName())
        .latitude(item.getShop().getGeom().getPosition().getLat())
        .longitude(item.getShop().getGeom().getPosition().getLon())
        .siDo(item.getShop().getSiDo())
        .siGunGu(item.getShop().getSiGunGu())
        .eupMyoenDong(item.getShop().getEupMyoenDong())
        .ri(item.getShop().getRi())
        .remainQuantity(item.getQuantity())
        .expiredDate(item.getExpiredDate())
        .pickupStartTime(item.getPickupStartTime())
        .pickupEndTime(item.getPickupEndTime())
        .pickupToday(item.getPickupStartTime().toLocalDate().isEqual(TODAY))
        .build();
  }
}
