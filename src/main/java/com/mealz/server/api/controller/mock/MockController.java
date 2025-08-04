package com.mealz.server.api.controller.mock;

import com.mealz.server.domain.mock.application.dto.request.LoginRequest;
import com.mealz.server.domain.mock.application.dto.response.LoginResponse;
import com.mealz.server.domain.mock.application.service.MockService;
import com.mealz.server.global.annotation.LogMonitoringInvocation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mock")
@Tag(
    name = "개발자 테스트용 API",
    description = "개발자 편의를 위한 테스트용 API 제공"
)
public class MockController implements MockControllerDocs {

  private final MockService mockService;

  @Override
  @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @LogMonitoringInvocation
  public ResponseEntity<LoginResponse> socialLogin(
      @Valid @ModelAttribute LoginRequest request) {
    return ResponseEntity.ok(mockService.testSocialLogin(request));
  }
}
