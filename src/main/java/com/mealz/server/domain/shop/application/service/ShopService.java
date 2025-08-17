package com.mealz.server.domain.shop.application.service;

import com.mealz.server.domain.member.application.service.MemberService;
import com.mealz.server.domain.member.core.constant.MemberType;
import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.shop.application.dto.request.ShopDistanceFilteredRequest;
import com.mealz.server.domain.shop.application.dto.request.ShopRequest;
import com.mealz.server.domain.shop.application.dto.response.ShopResponse;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import com.mealz.server.domain.shop.infrastructure.repository.ShopRepository;
import com.mealz.server.domain.shop.infrastructure.repository.ShopRepositoryCustom;
import com.mealz.server.domain.storage.core.constant.UploadType;
import com.mealz.server.domain.storage.core.service.StorageService;
import com.mealz.server.domain.storage.infrastructure.util.FileUtil;
import com.mealz.server.global.exception.CustomException;
import com.mealz.server.global.exception.ErrorCode;
import com.mealz.server.global.util.PostGisUtil;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopService {

  private final ShopRepository shopRepository;
  private final ShopRepositoryCustom shopRepositoryCustom;
  private final MemberService memberService;
  private final StorageService storageService;

  @Transactional
  public void createShop(Member member, ShopRequest request) {
    memberService.validateMemberType(member, MemberType.DONATOR);
    String shopProfileUrl = null;
    if (!FileUtil.isNullOrEmpty(request.getShopImage())) {
      shopProfileUrl = storageService.uploadFile(request.getShopImage(), UploadType.SHOP);
    }

    Shop shop = Shop.builder()
        .member(member)
        .shopName(request.getShopName())
        .shopCategory(request.getShopCategory())
        .shopImageUrl(shopProfileUrl)
        .shopDescription(request.getShopDescription())
        .geom(PostGisUtil.makePoint(request.getLongitude(), request.getLatitude()))
        .siDo(request.getSiDo())
        .siGunGu(request.getSiGunGu())
        .eupMyoenDong(request.getEupMyoenDong())
        .ri(request.getRi())
        .shopPhoneNumber(request.getShopPhoneNumber())
        .openTime(request.getOpenTime())
        .closeTime(request.getCloseTime())
        .build();
    shopRepository.save(shop);
  }

  @Transactional(readOnly = true)
  public Page<ShopResponse> filteredShopByDistance(ShopDistanceFilteredRequest request) {
    Page<Shop> shops = shopRepositoryCustom.filteredShopByDistance(
        request.getLongitude(),
        request.getLatitude(),
        request.getRadiusInMeters(),
        request.toPageable()
    );
    return shops.map(ShopResponse::from);
  }

  @Transactional(readOnly = true)
  public List<ShopResponse> getMyShops(UUID memberId) {
    Member member = memberService.findMemberById(memberId);
    List<Shop> shops = shopRepository.findByMember(member);
    return shops.stream()
        .map(ShopResponse::from)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public ShopResponse getShop(UUID shopId) {
    return ShopResponse.from(findShopById(shopId));
  }

  @Transactional(readOnly = true)
  public List<ShopResponse> getDonateShops(int donateCount) {
    return shopRepository.findAllByDonateCountAfterOrderByDonateCountDesc(donateCount - 1)
        .stream()
        .map(ShopResponse::from)
        .collect(Collectors.toList());
  }

  public Shop findShopById(UUID shopId) {
    return shopRepository.findById(shopId)
        .orElseThrow(() -> {
          log.error("요청된 PK값에 해당하는 가게를 찾을 수 없습니다. 요청 PK: {}", shopId);
          return new CustomException(ErrorCode.SHOP_NOT_FOUND);
        });
  }

  public void validateOwnedShop(Member member, Shop shop) {
    if (!shop.getMember().getMemberId().equals(member.getMemberId())) {
      log.error("가게 소유주가 아닙니다: 요청 가게: {}, 요청 회원: {}", shop.getShopId(), member.getMemberId());
      throw new CustomException(ErrorCode.NOT_OWNED_SHOP);
    }
  }
}
