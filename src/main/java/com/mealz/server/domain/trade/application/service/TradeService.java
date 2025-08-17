package com.mealz.server.domain.trade.application.service;

import com.mealz.server.domain.item.application.service.ItemImageService;
import com.mealz.server.domain.item.application.service.ItemService;
import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.item.infrastructure.entity.ItemImage;
import com.mealz.server.domain.item.infrastructure.repository.ItemRepository;
import com.mealz.server.domain.member.application.service.MemberService;
import com.mealz.server.domain.member.core.constant.MemberType;
import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import com.mealz.server.domain.shop.infrastructure.repository.ShopRepository;
import com.mealz.server.domain.trade.application.dto.request.TradeFilteredRequest;
import com.mealz.server.domain.trade.application.dto.request.TradeRequest;
import com.mealz.server.domain.trade.application.dto.response.TradeResponse;
import com.mealz.server.domain.trade.core.constant.TradeStatus;
import com.mealz.server.domain.trade.infrastructure.entity.Trade;
import com.mealz.server.domain.trade.infrastructure.repository.TradeRepository;
import com.mealz.server.domain.trade.infrastructure.repository.TradeRepositoryCustom;
import com.mealz.server.global.exception.CustomException;
import com.mealz.server.global.exception.ErrorCode;
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
public class TradeService {

  private final TradeRepository tradeRepository;
  private final TradeRepositoryCustom tradeRepositoryCustom;
  private final MemberService memberService;
  private final ItemService itemService;
  private final ItemRepository itemRepository;
  private final ItemImageService itemImageService;
  private final ShopRepository shopRepository;

  @Transactional
  public void createTrade(Member member, TradeRequest request) {
    memberService.validateMemberType(member, MemberType.BENEFICIARY);
    Item item = itemService.findItemById(request.getItemId());
    if (request.getQuantity() > item.getQuantity()) {
      log.error("요청 수량이 해당 물품 최대 수량을 초과했습니다. 요청수량: {}, 물품 최대 수량: {}",
          request.getQuantity(), item.getQuantity());
      throw new CustomException(ErrorCode.TRADE_QUANTITY_EXCEED);
    }
    item.setQuantity(item.getQuantity() - request.getQuantity());
    Item savedItem = itemRepository.save(item);

    // 매장 나눔 횟수 증가
    Shop shop = item.getShop();
    shop.setDonateCount(shop.getDonateCount() + 1);
    shopRepository.save(shop);

    Trade trade = Trade.builder()
        .beneficiary(member)
        .item(savedItem)
        .tradeStatus(TradeStatus.PENDING)
        .quantity(request.getQuantity())
        .build();
    tradeRepository.save(trade);
  }

  @Transactional(readOnly = true)
  public TradeResponse getTrade(UUID tradeId) {
    Trade trade = findTradeById(tradeId);
    List<ItemImage> itemImages = itemImageService.getImagesByItem(trade.getItem());
    return TradeResponse.from(trade, trade.getItem(), itemImages);
  }

  @Transactional(readOnly = true)
  public Page<TradeResponse> filteredTrade(TradeFilteredRequest request) {
    Page<Trade> tradePage = tradeRepositoryCustom.filteredTrade(
        request.getDonatorId(),
        request.getBeneficiaryId(),
        request.getShopId(),
        request.getItemId(),
        request.getDate(),
        request.getTradeStatus(),
        request.toPageable()
    );

    return tradePage.map(trade -> {
      List<ItemImage> images = itemImageService.getImagesByItem(trade.getItem());
      return TradeResponse.from(trade, trade.getItem(), images);
    });
  }

  @Transactional
  public void tradeSucceed(Member member, UUID tradeId) {
    Trade trade = findTradeById(tradeId);
    validateTradeStatus(trade, TradeStatus.PENDING);
    trade.setTradeStatus(TradeStatus.SUCCEED);
    tradeRepository.save(trade);
  }

  public Trade findTradeById(UUID tradeId) {
    return tradeRepository.findById(tradeId)
        .orElseThrow(() -> {
          log.error("요청 PK에 해당하는 거래를 찾을 수 없습니다. 요청 PK: {}", tradeId);
          return new CustomException(ErrorCode.TRADE_NOT_FOUND);
        });
  }

  private void validateTradeStatus(Trade trade, TradeStatus tradeStatus) {
    if (!trade.getTradeStatus().equals(tradeStatus)) {
      log.error("거래 상태 오류: 현재 거래 상태: {}, 검증할 거래 상태: {}", trade.getTradeStatus(), tradeStatus);
      throw new CustomException(ErrorCode.INVALID_TRADE_STATUS);
    }
  }
}
