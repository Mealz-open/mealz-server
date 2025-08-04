package com.mealz.server.api.controller.member;

import com.mealz.server.domain.auth.infrastructure.oauth2.CustomOAuth2User;
import com.mealz.server.domain.member.application.dto.request.MemberInfoRequest;
import com.mealz.server.domain.member.application.dto.response.MemberInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface MemberControllerDocs {

  @Operation(
      summary = "내 정보 조회",
      description = """
          이 API는 인증이 필요합니다.
          인증된 사용자의 정보를 조회합니다.
          
          ### 반환값
          ```json
            {
              "memberId": "269e1e88-a187-4b93-b3b0-71e9003e9b22",
              "username": "email@naver.com",
              "nickname": "재빠른 다람쥐",
              "name": "백지훈",
              "memberType": "DONATOR",
              "birthDay": "0701",
              "birthYear": "2002",
              "phone": "010-1234-5678",
              "profileUrl": null,
              "gender": "female",
            }
          ```
          
          ### 유의사항
          - 이 API는 JWT 기반 인증이 필요하며, AccessToken이 유효해야 합니다.
          - 사용자는 자신의 정보만 조회할 수 있으며, 서버는 인증된 사용자 정보를 기반으로 자동 조회합니다.
          """
  )
  ResponseEntity<MemberInfoResponse> getMemberInfo(
      CustomOAuth2User customOAuth2User);

  @Operation(
      summary = "회원 정보 설정",
      description = """
          ### 요청 파라미터
          - `nickname` (String, required): 3~10글자 사이의 닉네임
          - `memberType` (MemberType, required): `MemberType` enum 값 입력
          - `profileImage` (MultipartFile, optional): 프로필 이미지 파일
          
          ### 응답 데이터
          - `없음`
          
          ### `MemberType
          - `DONATOR`: 기부자
          - `BENEFICIARY`: 수혜자
          
          ### 사용 방법
          1. `multipart/form-data` 형태로 다음 필드들을 포함하여 요청을 전송합니다.
          2. 서버에서 닉네임과 멤버 유형을 업데이트하고, 프로필 이미지를 업로드한 뒤 프로필 URL을 저장합니다.
          3. 정상 처리 시 HTTP `200 OK`를 반환합니다
          
          ### 유의 사항
          - `nickname`은 반드시 3자 이상 10자 이하이어야 하며, 누락 시 400 에러가 발생합니다.
          - `memberType`은 `DONOR`, `RECIPIENT` `MemberType` enum에 정의된 값만 허용됩니다.
          - `profileImage`는 선택 항목으로, 전송하지 않아도 요청이 정상 처리됩니다.
          """
  )
  ResponseEntity<Void> setMemberInfo(
      CustomOAuth2User customOAuth2User,
      MemberInfoRequest request
  );
}
