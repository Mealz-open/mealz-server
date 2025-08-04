package com.mealz.server.api.controller.mock;

import com.mealz.server.domain.mock.application.dto.request.LoginRequest;
import com.mealz.server.domain.mock.application.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface MockControllerDocs {

  @Operation(
      summary = "테스트 로그인",
      description = """
          
          이 API는 인증이 필요하지 않습니다.
          
          ### 요청 파라미터
          - **role** (String): 회원 권한 [필수]
          - **socialPlatform** (String): 소셜 플랫폼 [필수]
          - **memberType** (String): 의뢰인/대리자 (기본: 의뢰인)
          - **accountStatus** (String): 활성화/삭제 (기본: 활성화)
          - **isFirstLogin** (Boolean): 첫 로그인 여부
          
          ### 유의사항
          - 개발자의 편의를 위한 소셜 로그인 회원가입/로그인 메서드입니다
          - 스웨거에서 테스트 용도로만 사용해야하며, 엑세스 토큰만 제공됩니다.
          - `ROLE_TEST`, `ROLE_TEST_ADMIN`만 선택 가능합니다
          - username을 입력하지 않을 시 임의의 사용자가 생성됩니다
          """
  )
  ResponseEntity<LoginResponse> socialLogin(LoginRequest request);
}
