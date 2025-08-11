package com.mealz.server.domain.search.application.service;

import com.mealz.server.domain.item.application.dto.response.ItemResponse;
import com.mealz.server.domain.item.application.service.ItemImageService;
import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.item.infrastructure.entity.ItemImage;
import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.search.application.dto.request.ItemSearchRequest;
import com.mealz.server.domain.search.application.dto.request.ShopSearchRequest;
import com.mealz.server.domain.search.infrastructure.entity.Search;
import com.mealz.server.domain.search.infrastructure.repository.SearchRepository;
import com.mealz.server.domain.search.infrastructure.repository.SearchRepositoryCustom;
import com.mealz.server.domain.shop.application.dto.response.ShopResponse;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

  private final SearchRepository searchRepository;
  private final SearchRepositoryCustom searchRepositoryCustom;
  private final ItemImageService itemImageService;
  private final SearchHistoryService searchHistoryService;

  @Transactional
  public Page<ShopResponse> searchShop(Member member, ShopSearchRequest request) {
    increaseSearchCount(request.getKeyword());
    searchHistoryService.saveMemberSearchHistory(member, request.getKeyword());
    Page<Shop> shopPage = searchRepositoryCustom.searchShop(
        request.getKeyword(),
        request.getLatitude(),
        request.getLongitude(),
        request.toPageable());
    return shopPage.map(ShopResponse::from);
  }

  @Transactional
  public Page<ItemResponse> searchItem(Member member, ItemSearchRequest request) {
    increaseSearchCount(request.getKeyword());
    searchHistoryService.saveMemberSearchHistory(member, request.getKeyword());
    Page<Item> itemPage = searchRepositoryCustom.searchItem(
        request.getKeyword(),
        request.toPageable()
    );
    return itemPage.map(item -> {
      List<ItemImage> images = itemImageService.getImagesByItem(item);
      return ItemResponse.from(item, images);
    });
  }

  @Transactional(readOnly = true)
  public List<String> getPopularKeyword() {
    return searchRepository.findTop30ByOrderByCountDesc()
        .stream()
        .map(Search::getKeyword)
        .collect(Collectors.toList());
  }

  @Transactional
  public void increaseSearchCount(String keyword) {
    Search search = searchRepository.findByKeyword(keyword)
        .orElseGet(() ->
            Search.builder()
                .keyword(keyword)
                .count(0)
                .build()
        );
    search.setCount(search.getCount() + 1);
    searchRepository.save(search);
  }

}
