package com.mealz.server.domain.item.application.service;

import com.mealz.server.domain.item.application.dto.request.ItemFilteredRequest;
import com.mealz.server.domain.item.application.dto.request.ItemRequest;
import com.mealz.server.domain.item.application.dto.response.ItemResponse;
import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.item.infrastructure.entity.ItemImage;
import com.mealz.server.domain.item.infrastructure.repository.ItemRepository;
import com.mealz.server.domain.item.infrastructure.repository.ItemRepositoryCustom;
import com.mealz.server.domain.member.application.service.MemberService;
import com.mealz.server.domain.member.core.constant.MemberType;
import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.shop.application.service.ShopService;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import com.mealz.server.global.exception.CustomException;
import com.mealz.server.global.exception.ErrorCode;
import com.mealz.server.global.util.CommonUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;
  private final ItemRepositoryCustom itemRepositoryCustom;
  private final ShopService shopService;
  private final ItemImageService itemImageService;
  private final MemberService memberService;

  @Transactional
  public void createItem(Member member, ItemRequest request) {
    Shop shop = shopService.findShopById(request.getShopId());
    memberService.validateMemberType(member, MemberType.DONATOR);
    shopService.validateOwnedShop(member, shop);
    validatePickupTime(request.getPickupStartDate(), request.getPickupEndDate());

    Item item = Item.builder()
        .shop(shop)
        .itemName(request.getItemName())
        .quantity(request.getQuantity())
        .expiredDate(request.getExpiredDate())
        .pickupStartTime(request.getPickupStartDate())
        .pickupEndTime(request.getPickupEndDate())
        .build();
    Item savedItem = itemRepository.save(item);

    if (!CommonUtil.nullOrEmpty(request.getItemImages())) {
      itemImageService.saveImages(savedItem, request.getItemImages());
    }
  }

  @Transactional(readOnly = true)
  public Page<ItemResponse> filteredItem(ItemFilteredRequest request) {
    Page<Item> itemPage = itemRepositoryCustom.filteredItem(
        request.getMemberId(),
        request.getShopId(),
        request.getDate(),
        request.getShopCategory(),
        request.toPageable()
    );

    return itemPage.map(item -> {
      List<ItemImage> images = itemImageService.getImagesByItem(item);
      return ItemResponse.from(item, images);
    });
  }

  @Transactional(readOnly = true)
  public ItemResponse getItem(UUID itemId) {
    Item item = findItemById(itemId);
    List<ItemImage> itemImages = itemImageService.getImagesByItem(item);
    return ItemResponse.from(item, itemImages);
  }

  public Item findItemById(UUID itemId) {
    return itemRepository.findById(itemId)
        .orElseThrow(() -> {
          log.error("요청된 PK에 해당하는 물품을 찾을 수 없습니다. 요청PK: {}", itemId);
          return new CustomException(ErrorCode.ITEM_NOT_FOUND);
        });
  }

  private void validatePickupTime(LocalDateTime pickupStartTime, LocalDateTime pickupEndTime) {
    if (pickupStartTime.isAfter(pickupEndTime)) {
      log.error("픽업 시작 시간이 픽업 종료 시간보다 늦습니다. 시작시간: {}, 종료시간: {}", pickupStartTime, pickupEndTime);
      throw new CustomException(ErrorCode.INVALID_PICKUP_TIME);
    }
  }
}
