package com.mealz.server.api.controller.trade;

import com.mealz.server.domain.auth.infrastructure.oauth2.CustomOAuth2User;
import com.mealz.server.domain.trade.application.dto.request.TradeFilteredRequest;
import com.mealz.server.domain.trade.application.dto.request.TradeRequest;
import com.mealz.server.domain.trade.application.dto.response.TradeResponse;
import com.mealz.server.domain.trade.application.service.TradeService;
import com.mealz.server.global.annotation.LogMonitoringInvocation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
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
    name = "거래 관련 API",
    description = "거래 관련 API 제공"
)
@RequestMapping("/api/trade")
public class TradeController implements TradeControllerDocs{

  private final TradeService tradeService;

  @Override
  @PostMapping
  @LogMonitoringInvocation
  public ResponseEntity<Void> createTrade(
      @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
      @Valid @RequestBody TradeRequest request) {
    tradeService.createTrade(customOAuth2User.getMember(), request);
    return ResponseEntity.ok().build();
  }

  @Override
  @GetMapping("/{trade-id}")
  @LogMonitoringInvocation
  public ResponseEntity<TradeResponse> getTrade(
      @PathVariable(name = "trade-id") UUID tradeId) {
    return ResponseEntity.ok(tradeService.getTrade(tradeId));
  }

  @Override
  @GetMapping
  @LogMonitoringInvocation
  public ResponseEntity<Page<TradeResponse>> filteredTrade(
      @ParameterObject TradeFilteredRequest request) {
    return ResponseEntity.ok(tradeService.filteredTrade(request));
  }
}
