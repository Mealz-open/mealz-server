package com.mealz.server.api.controller.shop;

import com.mealz.server.domain.auth.infrastructure.oauth2.CustomOAuth2User;
import com.mealz.server.domain.shop.application.dto.request.ShopRequest;
import com.mealz.server.domain.shop.application.dto.response.ShopResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public interface ShopControllerDocs {

  @Operation(
      summary = "매장 등록",
      description = """
          ### 요청 파라미터
          - `shopName` (String, required): 매장 이름
          - `shopCategory` (String, required): 매장 카테고리
          - `latitude` (long, required): 위도 (EPSG:4326)
          - `longitude` (long, required): 경도 (EPSG:4326)
          - `siDo` (String, required): 시/도
          - `siGunGu` (String, required): 시/군/구
          - `eupMyoenDong` (String, required): 읍/면/동
          - `ri` (String, optional): 리
          
          ### `ShopCategory`
          KOREAN("한식")
          CHINESE("중식")
          JAPANESE("일식")
          WESTERN("양식")
          ASIAN("아시안")
          SNACK("분식")
          FAST_FOOD("패스트푸드")
          DESSERT("디저트")
          BEVERAGE("음료")
          SIDE_DISH("반찬")
          ETC("기타")
          
          ### 응답 데이터
          - HTTP 200 OK, Body 없음
          
          ### 사용 방법
          1. DONATOR 타입 회원으로 로그인 후, Authorization 헤더에 엑세스 토큰을 포함합니다.  
          2. JSON 형태로 요청 본문을 구성하여 POST `/api/shop` 로 전송합니다.
          
          ### 유의 사항
          - 모든 필드(`shopName`, `shopCategory`, `latitude`, `longitude`, `siDo`, `siGunGu`, `eupMyoenDong`)는 필수입니다.
          - `ri`는 필요 없는 경우 빈 문자열 또는 생략 가능합니다.
          - DONATOR 타입 회원만 매장 등록이 가능합니다.
          """
  )
  ResponseEntity<Void> createShop(
      CustomOAuth2User customOAuth2User,
      ShopRequest request
  );

  @Operation(
      summary = "내 매장 목록 조회",
      description = """
          ### 요청 파라미터
          - `member-id` (UUID, path): 사용자 ID
          
          ### 응답 데이터
          - `List<ShopResponse>` 형태의 JSON 배열  
            - `id` (UUID): 매장 식별자  
            - `nickname` (String): 매장주 닉네임  
            - `profileUrl` (String): 매장주 프로필 이미지 URL  
            - `shopName` (String): 매장 이름  
            - `shopCategory` (String): 매장 카테고리  
            - `latitude` (Double): 위도  
            - `longitude` (Double): 경도  
            - `siDo` (String): 시/도  
            - `siGunGu` (String): 시/군/구  
            - `eupMyoenDong` (String): 읍/면/동  
            - `ri` (String): 리
          
          ### 사용 방법
          1. 로그인 후 Authorization 헤더에 엑세스 토큰을 포함합니다.  
          2. GET `/api/shop` 엔드포인트 호출 시, 본인의 매장 리스트를 반환합니다.
          
          ### 유의 사항
          - 토큰이 만료되었거나 유효하지 않으면 401 Unauthorized가 반환됩니다.
          """
  )
  ResponseEntity<List<ShopResponse>> getMyShops(
      UUID memberId
  );

  @Operation(
      summary = "매장 상세 조회",
      description = """
          ### 요청 파라미터
          - `shop-id` (UUID, path): 조회할 매장의 식별자
          
          ### 응답 데이터
          - `ShopResponse` 형태의 JSON 객체
            - `id` (UUID): 매장 식별자  
            - `nickname` (String): 매장주 닉네임  
            - `profileUrl` (String): 매장주 프로필 이미지 URL  
            - `shopName` (String): 매장 이름  
            - `shopCategory` (String): 매장 카테고리
            - `latitude` (Double): 위도  
            - `longitude` (Double): 경도  
            - `siDo` (String): 시/도  
            - `siGunGu` (String): 시/군/구  
            - `eupMyoenDong` (String): 읍/면/동  
            - `ri` (String): 리
          
          ### 사용 방법
          1. GET `/api/shop/{shop-id}` 경로에 조회할 매장 ID를 넣어 호출합니다.
          
          ### 유의 사항
          - 존재하지 않는 `shop-id`로 조회 시 400 또는 404 에러가 발생할 수 있습니다.
          """
  )
  ResponseEntity<ShopResponse> getShop(
      UUID shopId
  );
}
