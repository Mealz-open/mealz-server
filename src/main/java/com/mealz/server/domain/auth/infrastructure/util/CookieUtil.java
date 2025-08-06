package com.mealz.server.domain.auth.infrastructure.util;

import com.mealz.server.domain.auth.infrastructure.constant.AuthConstants;
import com.mealz.server.global.exception.CustomException;
import com.mealz.server.global.exception.ErrorCode;
import jakarta.servlet.http.Cookie;
import java.util.Arrays;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;

@UtilityClass
@Slf4j
public class CookieUtil {

  /**
   * 새로운 쿠키를 발급합니다
   *
   * @return 발급된 쿠키를 반환합니다
   */
  public ResponseCookie createCookie(String key, String token, long expirationTimeInSeconds) {
    if (key.equals(AuthConstants.ACCESS_TOKEN_KEY)) {
      return createAccessTokenCookie(token, expirationTimeInSeconds);
    } else if (key.equals(AuthConstants.REFRESH_TOKEN_KEY)) {
      return createRefreshTokenCookie(token, expirationTimeInSeconds);
    } else {
      log.error("잘못된 Cookie key가 요청됐습니다. 요청값: {}", key);
      throw new CustomException(ErrorCode.INVALID_REQUEST);
    }
  }

  /**
   * 쿠키의 setMaxAge를 0으로 설정하여 반홥합니다
   *
   * @param cookie 삭제하고 싶은 쿠키
   */
  public Cookie deleteCookie(Cookie cookie) {
    cookie.setMaxAge(0);
    cookie.setPath("/");
    return cookie;
  }

  /**
   * Cookie[]에서 특정 cookieName의 쿠키를 반환합니다
   */
  public Cookie extractedByCookieName(Cookie[] cookies, String cookieName) {
    if (cookies == null) {
      throw new CustomException(ErrorCode.COOKIES_NOT_FOUND);
    }
    return Arrays.stream(cookies)
        .filter(cookie -> cookie.getName().equals(cookieName))
        .findFirst()
        .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REQUEST));
  }

  /**
   * 엑세스 토큰이 들어있는 쿠키를 발급합니다
   * httpOnly = false
   */
  private ResponseCookie createAccessTokenCookie(String accessToken, long expirationTimeInSeconds) {
    log.debug("accessToken을 포함한 쿠키를 발급합니다.");
    return ResponseCookie.from(AuthConstants.ACCESS_TOKEN_KEY, accessToken)
        .path("/")
        .httpOnly(false)
        .secure(false)
        .sameSite("None")
        .maxAge(expirationTimeInSeconds)
        .build();
  }

  /**
   * 리프레시 토큰이 들어있는 쿠키를 발급합니다.
   * httpOnly = true
   */
  private ResponseCookie createRefreshTokenCookie(String refreshToken, long expirationTimeInSeconds) {
    log.debug("refreshToken을 포함한 쿠키를 발급합니다.");
    return ResponseCookie.from(AuthConstants.REFRESH_TOKEN_KEY, refreshToken)
        .path("/")
        .httpOnly(true)
        .secure(false)
        .sameSite("None")
        .maxAge(expirationTimeInSeconds)
        .build();
  }
}
