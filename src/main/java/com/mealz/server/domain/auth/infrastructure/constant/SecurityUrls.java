package com.mealz.server.domain.auth.infrastructure.constant;

import java.util.List;
import lombok.experimental.UtilityClass;

/**
 * Security 관련 URL 상수 관리
 */
@UtilityClass
public class SecurityUrls {

  /**
   * 인증을 생략할 URL 패턴 목록
   */
  public static final List<String> AUTH_WHITELIST = List.of(

      // API
      "/api/auth/reissue", // accessToken 재발급
      "/login/oauth2/code/**", // 소셜 로그인
      "/login/**",
      "/api/item", // 오늘의 나눔 (물품 필터링)

      // Swagger
      "/docs/swagger-ui/**", // Swagger UI
      "/v3/api-docs/**", // Swagger API 문서

      // Mock
      "/mock/**",

      "**/favicon.ico"

  );

  /**
   * 관리자 권한이 필요한 URL 패턴 목록
   */
  public static final List<String> ADMIN_PATHS = List.of(
      // API
  );
}
