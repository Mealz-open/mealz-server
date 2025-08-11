package com.mealz.server.api.controller.shop;

import com.mealz.server.domain.auth.infrastructure.oauth2.CustomOAuth2User;
import com.mealz.server.domain.shop.application.dto.request.ShopDistanceFilteredRequest;
import com.mealz.server.domain.shop.application.dto.request.ShopRequest;
import com.mealz.server.domain.shop.application.dto.response.ShopResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ShopControllerDocs {

  @Operation(
      summary = "매장 등록",
      description = """
          ### 요청 파라미터
          - `shopName` (String, required): 매장 이름
          - `shopCategory` (String, required): 매장 카테고리
          - `shopImage` (MultipartFile, optional): 매장 프로필 사진
          - `shopDescription` (String, optional): 매장 설명
          - `latitude` (double, required): 위도 (EPSG:4326)
          - `longitude` (double, required): 경도 (EPSG:4326)
          - `siDo` (String, required): 시/도
          - `siGunGu` (String, required): 시/군/구
          - `eupMyoenDong` (String, required): 읍/면/동
          - `ri` (String, optional): 리
          - `shopPhoneNumber` (String, optional): 전화번호 (예: 02-1234-5678)
          - `openTime` (String, optional): 오픈 시간 (HH:mm)
          - `closeTime` (String, optional): 마감 시간 (HH:mm)
          
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
          1. DONATOR 타입 회원으로 로그인 후, Authorization 헤더에 액세스 토큰을 포함합니다.
          2. FormData 형태로 요청 본문을 구성하여 POST `/api/shop`로 전송합니다.
          
          ### 유의 사항
          - 필드(`shopName`, `shopCategory`, `latitude`, `longitude`, `siDo`, `siGunGu`, `eupMyoenDong`)는 필수입니다.
          - `ri`, `shopImage`, `shopDescription`, `shopPhoneNumber`, `openTime`, `closeTime`은 필요 없는 경우 생략 가능합니다.
          - `shopPhoneNumber`는 `\\d{2,3}-\\d{3,4}-\\d{4}` 형식이어야 합니다.
          - DONATOR 타입 회원만 매장 등록이 가능합니다.
          """
  )
  ResponseEntity<Void> createShop(
      CustomOAuth2User customOAuth2User,
      ShopRequest request
  );

  @Operation(
      summary = "매장 거리별 필터링 조회",
      description = """
          ### 요청 파라미터
          - `longitude` (double, required): 기준이 되는 경도 값
          - `latitude` (double, required): 기준이 되는 위도 값
          - `radiusInMeters` (double, required): 검색 반경(미터 단위)
          - `pageNumber` (int, optional): 조회할 페이지 번호 (1부터 시작, 기본값: 1)
          - `pageSize` (int, optional): 한 페이지당 결과 수 (기본값: 설정된 기본 페이지 크기)
          - `sortField` (ShopSortField, optional): 정렬 기준  
            - `CREATED_DATE`: 생성일 순  
            - `NEAREST`: 거리 순  
            (기본값: `CREATED_DATE`)
          - `sortDirection` (Sort.Direction, optional): 정렬 방향 (`ASC`, `DESC`, 기본값: `DESC`)
          
          ### 응답 데이터
          - `content` (List\\<ShopResponse\\>): 필터링된 매장 목록  
            - `shopId` (UUID): 매장 식별자  
            - `nickname` (String): 매장주 닉네임  
            - `profileUrl` (String): 매장주 프로필 이미지 URL  
            - `shopName` (String): 매장 이름  
            - `shopCategory` (ShopCategory): 매장 카테고리  
            - `shopImageUrl` (String): 매장 대표 이미지 URL  
            - `shopDescription` (String): 매장 설명  
            - `longitude` (Double): 매장 경도  
            - `latitude` (Double): 매장 위도  
            - `siDo` (String): 시·도  
            - `siGunGu` (String): 시·군·구  
            - `eupMyoenDong` (String): 읍·면·동  
            - `ri` (String): 리  
            - `shopPhoneNumber` (String): 매장 연락처  
            - `openTime` (String): 오픈 시각 (HH:mm, Asia/Seoul)  
            - `closeTime` (String): 마감 시각 (HH:mm, Asia/Seoul)  
            - `donateCount` (int): 기부 횟수  
          - `pageable`: 요청된 페이지 정보  
          - `totalElements` (long): 전체 매장 수  
          - `totalPages` (int): 전체 페이지 수  
          - `number` (int): 현재 페이지 인덱스(0부터 시작)  
          - `size` (int): 한 페이지당 요소 수  
          - `first` (boolean): 첫 페이지 여부  
          - `last` (boolean): 마지막 페이지 여부
          
          ### 사용 방법
          - 지정된 반경 내의 매장을 거리 또는 생성일 기준으로 정렬하여 페이징된 형태로 반환합니다.  
          
          ### 유의 사항
          - `longitude`, `latitude`는 필수로 입력해야합니다
          - `radiusInMeters`는 양수만 허용됩니다.    
          - 페이지 번호(`pageNumber`)가 1 미만일 경우 자동으로 1로 처리됩니다.  
          """
  )
  ResponseEntity<Page<ShopResponse>> filteredShopByDistance(
      ShopDistanceFilteredRequest request
  );

  @Operation(
      summary = "내 매장 목록 조회",
      description = """
          ### 요청 파라미터
          - `member-id` (UUID, path): 사용자 ID
          
          ### 응답 데이터
          - HTTP 200 OK, Body: `List<ShopResponse>` 형태의 JSON 배열
            - `shopId` (UUID): 매장 식별자
            - `nickname` (String): 매장주 닉네임
            - `profileUrl` (String): 매장주 프로필 이미지 URL
            - `shopName` (String): 매장 이름
            - `shopCategory` (String): 매장 카테고리
            - `shopImageUrl` (String): 매장 대표 사진 URL
            - `shopDescription` (String): 매장 설명
            - `longitude` (Double): 경도
            - `latitude` (Double): 위도
            - `siDo` (String): 시/도
            - `siGunGu` (String): 시/군/구
            - `eupMyoenDong` (String): 읍/면/동
            - `ri` (String): 리
            - `shopPhoneNumber` (String): 전화번호
            - `openTime` (String): 오픈 시간 (HH:mm)
            - `closeTime` (String): 마감 시간 (HH:mm)
            - `donateCount` (int): 기부 횟수
          
          ### 사용 방법
          1. 로그인 후 Authorization 헤더에 액세스 토큰을 포함합니다.
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
          - HTTP 200 OK, Body: `ShopResponse` 형태의 JSON 객체
            - `shopId` (UUID): 매장 식별자
            - `nickname` (String): 매장주 닉네임
            - `profileUrl` (String): 매장주 프로필 이미지 URL
            - `shopName` (String): 매장 이름
            - `shopCategory` (String): 매장 카테고리
            - `shopImageUrl` (String): 매장 대표 사진 URL
            - `shopDescription` (String): 매장 설명
            - `longitude` (Double): 경도
            - `latitude` (Double): 위도
            - `siDo` (String): 시/도
            - `siGunGu` (String): 시/군/구
            - `eupMyoenDong` (String): 읍/면/동
            - `ri` (String): 리
            - `shopPhoneNumber` (String): 전화번호
            - `openTime` (String): 오픈 시간 (HH:mm)
            - `closeTime` (String): 마감 시간 (HH:mm)
            - `donateCount` (int): 기부 횟수
          
          ### 사용 방법
          1. GET `/api/shop/{shop-id}` 경로에 조회할 매장 ID를 넣어 호출합니다.
          
          ### 유의 사항
          - 존재하지 않는 `shop-id`로 조회 시 400 또는 404 에러가 발생할 수 있습니다.
          """
  )
  ResponseEntity<ShopResponse> getShop(
      UUID shopId
  );

  @Operation(
      summary = "기부 카운트 기준 가게 목록 조회 (최대 30개)",
      description = """
          ### 요청 파라미터
          - (없음)
          
          ### 응답 데이터
          - `List<ShopResponse>`: 최대 30개의 가게 정보
          - `ShopResponse` 필드
            - `shopId` (UUID)
            - `nickname` (String) — `Shop.member.nickname`에서 매핑
            - `profileUrl` (String) — `Shop.member.profileUrl`에서 매핑
            - `shopName` (String)
            - `shopCategory` (ShopCategory: KOREAN, CHINESE, JAPANESE, WESTERN, ASIAN, SNACK, FAST_FOOD, DESSERT, BEVERAGE, SIDE_DISH, ETC)
            - `shopImageUrl` (String)
            - `shopDescription` (String)
            - `longitude` (Double) — 경도 (`Shop.geom.position.lon`)
            - `latitude` (Double) — 위도 (`Shop.geom.position.lat`)
            - `siDo` (String)
            - `siGunGu` (String)
            - `eupMyoenDong` (String)
            - `ri` (String)
            - `shopPhoneNumber` (String)
            - `openTime` (String, `HH:mm`, Asia/Seoul)
            - `closeTime` (String, `HH:mm`, Asia/Seoul)
            - `donateCount` (int)
          
          #### 응답 예시 (요약)
          ```json
          [
            {
              "shopId": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
              "nickname": "...",
              "profileUrl": "...",
              "shopName": "...",
              "shopCategory": "SNACK",
              "shopImageUrl": "...",
              "shopDescription": "...",
              "longitude": 127.0000,
              "latitude": 37.0000,
              "siDo": "...",
              "siGunGu": "...",
              "eupMyoenDong": "...",
              "ri": null,
              "shopPhoneNumber": "...",
              "openTime": "09:00",
              "closeTime": "21:00",
              "donateCount": 12
            }
          ]
          ```
          
          ### 사용 방법
          - **GET** `/api/shop/donate` 호출 시, `donateCount` 내림차순으로 정렬된 최대 30개의 가게를 반환합니다.
          
          ### 유의 사항
          - 시간 필드 포맷은 `HH:mm`(Asia/Seoul)입니다.
          """
  )
  ResponseEntity<List<ShopResponse>> getDonateShops();
}
