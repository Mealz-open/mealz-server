package com.mealz.server.domain.mock.application.service;

import static com.mealz.server.domain.member.core.constant.Role.ROLE_TEST;
import static com.mealz.server.domain.member.core.constant.Role.ROLE_TEST_ADMIN;

import com.mealz.server.domain.auth.core.service.TokenProvider;
import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.item.infrastructure.entity.ItemImage;
import com.mealz.server.domain.item.infrastructure.repository.ItemImageRepository;
import com.mealz.server.domain.item.infrastructure.repository.ItemRepository;
import com.mealz.server.domain.member.core.constant.MemberType;
import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.member.infrastructure.repository.MemberRepository;
import com.mealz.server.domain.mock.application.dto.request.LoginRequest;
import com.mealz.server.domain.mock.application.dto.response.LoginResponse;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import com.mealz.server.domain.shop.infrastructure.repository.ShopRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MockService {

  private final MemberRepository memberRepository;
  private final MockMemberFactory mockMemberFactory;
  private final MockShopFactory mockShopFactory;
  private final MockItemFactory mockItemFactory;
  private final TokenProvider tokenProvider;
  private final Faker koFaker;
  private final ShopRepository shopRepository;
  private final ItemRepository itemRepository;
  private final ItemImageRepository itemImageRepository;

  /*
  ======================================회원======================================
   */

  /**
   * 개발자용 테스트 로그인 로직
   * DB에 테스트 유저를 만든 후, 해당 사용자의 엑세스 토큰을 발급합니다.
   *
   * @param request role 권한 (ROLE_TEST / ROLE_TEST_ADMIN)
   *                socialPlatform 네이버/카카오 소셜 로그인 플랫폼
   *                memberType 의로인/대리인
   *                accountStatus 활성화/삭제
   *                isFirstLogin 첫 로그인 여부
   */
  @Transactional
  public LoginResponse testSocialLogin(LoginRequest request) {

    log.debug("테스트 계정 로그인을 집행합니다. 요청 소셜 플랫폼: {}", request.getSocialPlatform());

    Member member = memberRepository.findByUsername(request.getUsername())
        .orElseGet(() -> memberRepository.saveAndFlush(mockMemberFactory.generate(request)));

    String accessToken = tokenProvider.createAccessToken(member.getMemberId().toString(), member.getUsername(), member.getRole().name());

    log.debug("테스트 로그인 성공: 엑세스 토큰 및 리프레시 토큰 생성");
    log.debug("테스트 accessToken = {}", accessToken);

    return LoginResponse.builder()
        .memberId(member.getMemberId())
        .accessToken(accessToken)
        .build();
  }

  /**
   * 데이터베이스에 저장되어있는 테스트 유저를 모두 삭제합니다
   */
  @Transactional
  public void deleteTestMember() {
    log.debug("데이터베이스에 저장된 테스트 유저를 모두 삭제합니다.");
    memberRepository.deleteAllByRole(ROLE_TEST);
    memberRepository.deleteAllByRole(ROLE_TEST_ADMIN);
  }

  @Transactional
  public void createMockMember(int count) {
    List<Member> members = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      members.add(mockMemberFactory.generate());
    }
    memberRepository.saveAll(members);
  }

  /*
  ======================================매장======================================
   */

  @Transactional
  public void createMockShop(int count) {
    List<Member> donators = memberRepository.findAllByMemberType(MemberType.DONATOR);

    List<Shop> shops = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      shops.add(mockShopFactory.generate(donators.get(koFaker.random().nextInt(donators.size()))));
    }
    shopRepository.saveAll(shops);
  }

  /*
  ======================================매장======================================
   */
  @Transactional
  public void createMockItem(int count) {
    List<Shop> shops = shopRepository.findAll();

    List<Item> items = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      items.add(mockItemFactory.generateItem(shops.get(koFaker.random().nextInt(shops.size()))));
    }
    List<Item> savedItems = itemRepository.saveAll(items);

    List<ItemImage> images = new ArrayList<>();
    for (Item item : savedItems) {
      images.addAll(mockItemFactory.generateImages(item));
    }
    itemImageRepository.saveAll(images);
  }

}
