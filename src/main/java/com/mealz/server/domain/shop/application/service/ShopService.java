package com.mealz.server.domain.shop.application.service;

import com.mealz.server.domain.member.application.service.MemberService;
import com.mealz.server.domain.member.core.constant.MemberType;
import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.shop.application.dto.request.ShopRequest;
import com.mealz.server.domain.shop.application.dto.response.ShopResponse;
import com.mealz.server.domain.shop.application.mapper.ShopMapper;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import com.mealz.server.domain.shop.infrastructure.repository.ShopRepository;
import com.mealz.server.global.exception.CustomException;
import com.mealz.server.global.exception.ErrorCode;
import com.mealz.server.global.util.PostGisUtil;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopService {

  private final ShopRepository shopRepository;
  private final MemberService memberService;
  private final ShopMapper shopMapper;

  @Transactional
  public void createShop(Member member, ShopRequest request) {
    if (memberService.isValidMemberType(member, MemberType.DONATOR)) {
      log.error("기부자만 가게 등록 가능. 요청 MemberType: {}", member.getMemberType());
      throw new CustomException(ErrorCode.INVALID_MEMBER_TYPE);
    }

    Shop shop = Shop.builder()
        .member(member)
        .shopName(request.getShopName())
        .geom(PostGisUtil.makePoint(request.getLatitude(), request.getLongitude()))
        .siDo(request.getSiDo())
        .siGunGu(request.getSiGunGu())
        .eupMyoenDong(request.getEupMyoenDong())
        .ri(request.getRi())
        .build();
    shopRepository.save(shop);
  }

  @Transactional(readOnly = true)
  public List<ShopResponse> getMyShops(UUID memberId) {
    Member member = memberService.findMemberById(memberId);
    List<Shop> shops = shopRepository.findByMember(member);
    return shops.stream()
        .map(shopMapper::toShopResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public ShopResponse getShop(UUID shopId) {
    return shopMapper.toShopResponse(findShopById(shopId));
  }

  public Shop findShopById(UUID shopId) {
    return shopRepository.findById(shopId)
        .orElseThrow(() -> {
          log.error("요청된 PK값에 해당하는 가게를 찾을 수 없습니다. 요청 PK: {}", shopId);
          return new CustomException(ErrorCode.SHOP_NOT_FOUND);
        });
  }

  private void validateOwnedShop(Member member, Shop shop) {
    if (!shop.getMember().equals(member)) {
      log.error("가게 소유주가 아닙니다: 요청 가게: {}, 요청 회원: {}", shop.getShopId(), member.getMemberId());
      throw new CustomException(ErrorCode.NOT_OWNED_SHOP);
    }
  }
}
