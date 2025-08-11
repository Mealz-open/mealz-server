package com.mealz.server.api.controller.search;

import com.mealz.server.domain.auth.infrastructure.oauth2.CustomOAuth2User;
import com.mealz.server.domain.item.application.dto.response.ItemResponse;
import com.mealz.server.domain.search.application.dto.request.ItemSearchRequest;
import com.mealz.server.domain.search.application.dto.request.ShopSearchRequest;
import com.mealz.server.domain.search.application.service.SearchHistoryService;
import com.mealz.server.domain.search.application.service.SearchService;
import com.mealz.server.domain.shop.application.dto.response.ShopResponse;
import com.mealz.server.global.annotation.LogMonitoringInvocation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(
    name = "검색 관련 API",
    description = "검색 관련 API 제공"
)
@RequestMapping("/api/search")
public class SearchController implements SearchControllerDocs{

  private final SearchService searchService;
  private final SearchHistoryService searchHistoryService;

  @Override
  @PostMapping("/shop")
  @LogMonitoringInvocation
  public ResponseEntity<Page<ShopResponse>> searchShop(
      @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
      @Valid @RequestBody ShopSearchRequest request) {
    return ResponseEntity.ok(searchService.searchShop(customOAuth2User.getMember(), request));
  }

  @Override
  @PostMapping("/item")
  @LogMonitoringInvocation
  public ResponseEntity<Page<ItemResponse>> searchItem(
      @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
      @Valid @RequestBody ItemSearchRequest request) {
    return ResponseEntity.ok(searchService.searchItem(customOAuth2User.getMember(), request));
  }

  @Override
  @GetMapping("/popular/keyword")
  @LogMonitoringInvocation
  public ResponseEntity<List<String>> getPopularKeyword() {
    return ResponseEntity.ok(searchService.getPopularKeyword());
  }

  @Override
  @GetMapping("/recent/keyword")
  @LogMonitoringInvocation
  public ResponseEntity<List<String>> getMemberRecentKeyword(
      @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
    return ResponseEntity.ok(searchHistoryService.getMemberRecentKeyword(customOAuth2User.getMember()));
  }
}
