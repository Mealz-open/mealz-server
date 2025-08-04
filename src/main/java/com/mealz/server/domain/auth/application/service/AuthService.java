package com.mealz.server.domain.auth.application.service;

import static com.mealz.server.domain.auth.infrastructure.constant.AuthConstants.ACCESS_TOKEN_KEY;
import static com.mealz.server.domain.auth.infrastructure.constant.AuthConstants.REFRESH_TOKEN_KEY;

import com.mealz.server.domain.auth.core.service.TokenProvider;
import com.mealz.server.domain.auth.core.service.TokenStore;
import com.mealz.server.domain.auth.infrastructure.properties.JwtProperties;
import com.mealz.server.domain.auth.infrastructure.util.AuthUtil;
import com.mealz.server.domain.auth.infrastructure.util.CookieUtil;
import com.mealz.server.domain.member.application.service.MemberService;
import com.mealz.server.domain.member.infrastructure.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

  private final TokenProvider tokenProvider;
  private final TokenStore tokenStore;
  private final JwtProperties jwtProperties;
  private final MemberService memberService;

  /**
   * 쿠키에 저장된 refreshToken을 통해 accessToken, refreshToken을 재발급합니다
   */
  @Transactional
  public void reissue(HttpServletRequest request, HttpServletResponse response) {

    log.debug("accessToken이 만료되어 재발급을 진행합니다.");

    // 쿠키에서 리프레시 토큰 추출 및 검증
    String refreshToken = AuthUtil.extractRefreshTokenFromRequest(request);

    // 사용자 정보 조회
    String memberId = tokenProvider.getMemberId(refreshToken);
    Member member = memberService.findMemberById(UUID.fromString(memberId));

    // 새로운 토큰 생성
    String newAccessToken = tokenProvider.createAccessToken(memberId, member.getUsername(), member.getRole().name());
    String newRefreshToken = tokenProvider.createRefreshToken(memberId, member.getUsername(), member.getRole().name());

    // 기존 refreshToken 삭제
    tokenStore.remove(refreshToken);

    // refreshToken 저장
    // RefreshToken을 Redis에 저장 (key: RT:memberId)
    tokenStore.save(AuthUtil.getRefreshTokenTtlKey(memberId), newRefreshToken, jwtProperties.refreshExpMillis());

    // 쿠키에 accessToken, refreshToken 추가
    response.addCookie(CookieUtil.createCookie(ACCESS_TOKEN_KEY, newAccessToken, jwtProperties.accessExpMillis() / 1000));
    response.addCookie(CookieUtil.createCookie(REFRESH_TOKEN_KEY, newRefreshToken, jwtProperties.refreshExpMillis() / 1000));
  }
}
