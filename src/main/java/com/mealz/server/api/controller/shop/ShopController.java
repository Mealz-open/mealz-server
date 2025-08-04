package com.mealz.server.api.controller.shop;

import com.mealz.server.domain.auth.infrastructure.oauth2.CustomOAuth2User;
import com.mealz.server.domain.shop.application.dto.request.ShopRequest;
import com.mealz.server.domain.shop.application.dto.response.ShopResponse;
import com.mealz.server.domain.shop.application.service.ShopService;
import com.mealz.server.global.annotation.LogMonitoringInvocation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(
    name = "매장 관련 API",
    description = "매장 관련 API 제공"
)
@RequestMapping("/api/shop")
public class ShopController implements ShopControllerDocs {

  private final ShopService shopService;

  @Override
  @PostMapping
  @LogMonitoringInvocation
  public ResponseEntity<Void> createShop(
      @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
      @Valid @RequestBody ShopRequest request
  ) {
    shopService.createShop(customOAuth2User.getMember(), request);
    return ResponseEntity.ok().build();
  }

  @Override
  @GetMapping("/{member-id}")
  @LogMonitoringInvocation
  public ResponseEntity<List<ShopResponse>> getMyShops(
      @PathVariable(name = "member-id") UUID memberId
  ) {
    return ResponseEntity.ok(shopService.getMyShops(memberId));
  }

  @Override
  @GetMapping("/{shop-id}")
  @LogMonitoringInvocation
  public ResponseEntity<ShopResponse> getShop(
      @PathVariable(name = "shop-id") UUID shopId
  ) {
    return ResponseEntity.ok(shopService.getShop(shopId));
  }
}
