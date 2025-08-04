package com.mealz.server.domain.shop.application.mapper;

import com.mealz.server.domain.shop.application.dto.response.ShopResponse;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShopMapper {

  // Shop -> ShopResponse
  @Mapping(source = "member.nickname", target = "nickname")
  @Mapping(source = "member.profileUrl", target = "profileUrl")
  ShopResponse toShopResponse(Shop shop);
}
