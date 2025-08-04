package com.mealz.server.api.controller.auth;

import com.mealz.server.domain.auth.application.service.AuthService;
import com.mealz.server.global.annotation.LogMonitoringInvocation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(
    name = "인증 관련 API",
    description = "회원 인증 관련 API 제공"
)
public class AuthController implements AuthControllerDocs {

  private final AuthService authService;

  @Override
  @PostMapping(value = "/reissue")
  @LogMonitoringInvocation
  public ResponseEntity<Void> reissue(
      HttpServletRequest request,
      HttpServletResponse response) {
    authService.reissue(request, response);
    return ResponseEntity.ok().build();
  }
}
