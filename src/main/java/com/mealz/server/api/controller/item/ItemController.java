package com.mealz.server.api.controller.item;

import com.mealz.server.domain.auth.infrastructure.oauth2.CustomOAuth2User;
import com.mealz.server.domain.item.application.dto.request.ItemFilteredRequest;
import com.mealz.server.domain.item.application.dto.request.ItemRequest;
import com.mealz.server.domain.item.application.dto.response.ItemResponse;
import com.mealz.server.domain.item.application.service.ItemService;
import com.mealz.server.global.annotation.LogMonitoringInvocation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(
    name = "물품 관련 API",
    description = "물품 관련 API 제공"
)
@RequestMapping("/api/item")
public class ItemController implements ItemControllerDocs {

  private final ItemService itemService;

  @Override
  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<Void> createItem(
      @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
      @Valid @ModelAttribute ItemRequest request) {
    itemService.createItem(customOAuth2User.getMember(), request);
    return ResponseEntity.ok().build();
  }

  @Override
  @GetMapping(value = "/filter")
  @LogMonitoringInvocation
  public ResponseEntity<Page<ItemResponse>> filteredItem(
      @Valid @ParameterObject ItemFilteredRequest request) {
    return ResponseEntity.ok(itemService.filteredItem(request));
  }

  @Override
  @GetMapping("/{item-id}")
  @LogMonitoringInvocation
  public ResponseEntity<ItemResponse> getItem(
      @PathVariable(name = "item-id") UUID itemId) {
    return ResponseEntity.ok(itemService.getItem(itemId));
  }
}
