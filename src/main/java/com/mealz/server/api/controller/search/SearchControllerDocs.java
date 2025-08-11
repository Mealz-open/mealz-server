package com.mealz.server.api.controller.search;

import com.mealz.server.domain.auth.infrastructure.oauth2.CustomOAuth2User;
import com.mealz.server.domain.item.application.dto.response.ItemResponse;
import com.mealz.server.domain.search.application.dto.request.ItemSearchRequest;
import com.mealz.server.domain.search.application.dto.request.ShopSearchRequest;
import com.mealz.server.domain.shop.application.dto.response.ShopResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface SearchControllerDocs {

  @Operation(
      summary = "가게 검색",
      description = """
          ### 요청 파라미터 (Request Body: ShopSearchRequest)
          - `keyword` (String, required): 검색어
          - `latitude` (double, required): 현재 위치 위도
          - `longitude` (double, required): 현재 위치 경도
          - `pageNumber` (int, optional, default: 1)
          - `pageSize` (int, optional, default: PageableConstants.DEFAULT_PAGE_SIZE)
          - `sortField` (ShopSortField, optional, default: CREATED_DATE)
            - 허용값: `CREATED_DATE`, `NEAREST`
          - `sortDirection` (Sort.Direction, optional, default: DESC)
          
          ### 필터링/정렬 로직 (Repository 기준)
          - 검색 조건(OR, 대소문자 무시 부분일치):
            - `shopName`, `shopDescription`, `siDo`, `siGunGu`, `eupMyoenDong`
          - 정렬:
            - `NEAREST`: 제공된 `latitude`/`longitude`와 가게 위치(`geom`) 간 거리(ST_Distance) 오름/내림차순
            - `CREATED_DATE`: 생성일 기준 정렬 (기본)
          
          ### 응답 데이터 (Page<ShopResponse>)
          - 페이징 응답은 Spring 기본 Page 직렬화 형태로 반환됩니다. 핵심은 `content` 배열의 각 요소가 `ShopResponse` 구조를 가집니다.
          - `ShopResponse` 필드
            - `shopId` (UUID)
            - `nickname` (String)
            - `profileUrl` (String)
            - `shopName` (String)
            - `shopCategory` (ShopCategory: KOREAN, CHINESE, JAPANESE, WESTERN, ASIAN, SNACK, FAST_FOOD, DESSERT, BEVERAGE, SIDE_DISH, ETC)
            - `shopImageUrl` (String)
            - `shopDescription` (String)
            - `longitude` (Double)  // 경도
            - `latitude` (Double)   // 위도
            - `siDo` (String)
            - `siGunGu` (String)
            - `eupMyoenDong` (String)
            - `ri` (String)
            - `shopPhoneNumber` (String)
            - `openTime` (String, HH:mm, Asia/Seoul)
            - `closeTime` (String, HH:mm, Asia/Seoul)
          
          #### 응답 예시 (요약)
          ```json
          {
            "content": [
              {
                "shopId": "4a5e6c2b-...-...",
                "nickname": "푸디",
                "profileUrl": "https://...",
                "shopName": "강남분식",
                "shopCategory": "SNACK",
                "shopImageUrl": "https://...",
                "shopDescription": "즉석떡볶이 전문",
                "longitude": 127.0276,
                "latitude": 37.4979,
                "siDo": "서울특별시",
                "siGunGu": "강남구",
                "eupMyoenDong": "역삼동",
                "ri": null,
                "shopPhoneNumber": "02-1234-5678",
                "openTime": "09:00",
                "closeTime": "21:00"
              }
            ],
            "...": "Spring Page 기본 필드(총원, 총페이지 등)"
          }
          ```
          
          ### 사용 방법
          1) 아래 형태로 POST 요청
          ```json
          {
            "keyword": "강남",
            "latitude": 37.4979,
            "longitude": 127.0276,
            "pageNumber": 1,
            "pageSize": 20,
            "sortField": "NEAREST",
            "sortDirection": "ASC"
          }
          ```
          2) `sortField=NEAREST` 사용 시 현재 위치(`latitude`, `longitude`)가 필수입니다.
          
          ### 유의 사항
          - 검색 시 서버 내부적으로 인기 검색어 카운트가 +1 되며, 회원의 검색 기록이 저장됩니다(동일 키워드는 중복 제거 후 최신으로 저장).
          """
  )
  ResponseEntity<Page<ShopResponse>> searchShop(
      CustomOAuth2User customOAuth2User,
      ShopSearchRequest request
  );

  @Operation(
      summary = "상품 검색",
      description = """
        ### 요청 파라미터 (Request Body: ItemSearchRequest)
        - `keyword` (String, required): 검색어
        - `pageNumber` (int, optional, default: 1)
        - `pageSize` (int, optional, default: PageableConstants.DEFAULT_PAGE_SIZE)
        - `sortField` (ItemSortField, optional, default: CREATED_DATE)
          - 허용값: `CREATED_DATE`, `PICKUP_TIME`
        - `sortDirection` (Sort.Direction, optional, default: DESC)

        ### 필터링/정렬 로직 (Repository 기준)
        - 검색 조건(OR, 대소문자 무시 부분일치):
          - `itemName`, `shop.shopName`
        - 정렬:
          - `PICKUP_TIME` → `pickupStartTime` 기준 정렬
          - `CREATED_DATE` → 생성일 기준 정렬 (default)

        ### 응답 데이터 (Page<ItemResponse>)
        - 페이징 응답(Page) 내 `content` 배열 요소 구조는 아래의 `ItemResponse`를 따릅니다.
        - `ItemResponse` 필드
          - `itemId` (UUID)
          - `itemName` (String)
          - `itemImageUrls` (List<String>)
          - `shopCategory` (ShopCategory: KOREAN, CHINESE, JAPANESE, WESTERN, ASIAN, SNACK, FAST_FOOD, DESSERT, BEVERAGE, SIDE_DISH, ETC)
          - `shopName` (String)
          - `latitude` (double)   // 위도
          - `longitude` (double)  // 경도
          - `siDo` (String)
          - `siGunGu` (String)
          - `eupMyoenDong` (String)
          - `ri` (String)
          - `quantity` (int)
          - `expiredDate` (String, yyyy-MM-dd'T'HH:mm:ss, Asia/Seoul)
          - `pickupStartTime` (String, yyyy-MM-dd'T'HH:mm:ss, Asia/Seoul)
          - `pickupEndTime` (String, yyyy-MM-dd'T'HH:mm:ss, Asia/Seoul)
          - `pickupToday` (boolean) — `pickupStartTime`의 날짜가 오늘(Asia/Seoul)과 같으면 true

        ### 사용 방법
        1) 아래 예시처럼 JSON 본문으로 **POST `/api/search/item`** 호출
        ```json
        {
          "keyword": "샌드위치",
          "pageNumber": 1,
          "pageSize": 20,
          "sortField": "PICKUP_TIME",
          "sortDirection": "ASC"
        }
        ```

        ### 유의 사항
        - 시간 필드는 Asia/Seoul 타임존 기준이며 `yyyy-MM-dd'T'HH:mm:ss` 포맷입니다.
        """
  )
  ResponseEntity<Page<ItemResponse>> searchItem(
      CustomOAuth2User customOAuth2User,
      ItemSearchRequest request
  );

  @Operation(
      summary = "인기 검색어 조회",
      description = """
          ### 요청 파라미터
          - 없음
          
          ### 응답 데이터
          - `List<String>`: 인기 검색어 목록 (30개)
            - 검색 횟수 내림차순으로 정렬하여 반환
          
          #### 응답 예시
          ```json
          ["떡볶이", "강남", "샌드위치", "..."]
          ```
          
          ### 사용 방법
          - GET `/api/search/popular/keyword` 호출
          """
  )
  ResponseEntity<List<String>> getPopularKeyword();

  @Operation(
      summary = "회원 최근 검색어 조회",
      description = """
          ### 요청 파라미터
          - 없음
          
          ### 응답 데이터
          - `List<String>`: 최근 검색어 목록 (30개)
          
          #### 응답 예시
          ```json
          ["강남", "샌드위치", "분식", "..."]
          ```
          
          ### 사용 방법
          - GET `/api/search/recent/keyword` 호출 (로그인 필요)
          """
  )
  ResponseEntity<List<String>> getMemberRecentKeyword(
      CustomOAuth2User customOAuth2User
  );

}
