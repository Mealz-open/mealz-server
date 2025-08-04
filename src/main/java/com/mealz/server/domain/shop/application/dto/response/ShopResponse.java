package com.mealz.server.domain.shop.application.dto.response;

import java.util.UUID;

public record ShopResponse(
    UUID id,
    String nickname,
    String profileUrl,
    String shopName,
    Double latitude,
    Double longitude,
    String siDo,
    String siGunGu,
    String eupMyoenDong,
    String ri
) {

}
