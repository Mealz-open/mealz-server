package com.mealz.server.api.controller.trade;

import com.mealz.server.domain.auth.infrastructure.oauth2.CustomOAuth2User;
import com.mealz.server.domain.trade.application.dto.request.TradeFilteredRequest;
import com.mealz.server.domain.trade.application.dto.request.TradeRequest;
import com.mealz.server.domain.trade.application.dto.response.TradeResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface TradeControllerDocs {

  @Operation(
      summary = "거래 신청",
      description = """
          ### 요청 파라미터 (Request Body: application/json)
          - `itemId` (UUID, required): 신청할 물품의 식별자
          - `quantity` (int, required, min=1): 신청 수량
          
          ### 응답 데이터
          - `없음`
          
          ### 사용 방법
          1. 클라이언트에서 `itemId`, `quantity`를 포함한 JSON으로 요청합니다.
          2. 서버는 해당 물품 재고에서 `quantity`만큼 차감하고 거래를 생성합니다.
          3. 생성된 거래의 초기 상태는 `PENDING`입니다.
          
          ### 유의 사항
          - 수혜자 회원(`MemberType.BENEFICIARY`)만 신청할 수 있습니다.
          - 신청 수량은 해당 물품의 현재 재고(`Item.quantity`) 이하여야 합니다.
          - 거래 생성 시 재고가 즉시 차감됩니다.
          """
  )
  ResponseEntity<Void> createTrade(
      CustomOAuth2User customOAuth2User,
      TradeRequest request
  );

  @Operation(
      summary = "거래 단건 조회",
      description = """
          ### 요청 파라미터 (Path)
          - `trade-id` (UUID, required): 조회할 거래의 식별자
          
          ### 응답 데이터 (`TradeResponse`)
          - `tradeId` (UUID): 거래 ID
          - `tradeStatus` (TradeStatus): 거래 상태
          - `tradeQuantity` (int): 신청 수량
          - `itemId` (UUID): 물품 ID
          - `itemName` (String): 물품 이름
          - `itemImageUrls` (List<String>): 물품 이미지 URL 목록 (없으면 빈 배열)
          - `shopCategory` (ShopCategory): 매장 카테고리
          - `shopName` (String): 매장 이름
          - `latitude` (double): 매장 위도
          - `longitude` (double): 매장 경도
          - `siDo` (String): 주소(시/도)
          - `siGunGu` (String): 주소(시/군/구)
          - `eupMyoenDong` (String): 주소(읍/면/동)
          - `ri` (String): 주소(리)
          - `remainQuantity` (int): 현재 남은 수량
          - `expiredDate` (String, `yyyy-MM-dd'T'HH:mm:ss`, Asia/Seoul)
          - `pickupStartTime` (String, `yyyy-MM-dd'T'HH:mm:ss`, Asia/Seoul)
          - `pickupEndTime` (String, `yyyy-MM-dd'T'HH:mm:ss`, Asia/Seoul)
          - `pickupToday` (boolean): 수령 시작일이 오늘(Asia/Seoul)인지 여부
          
          ### 사용 방법
          - Path 변수로 전달한 `trade-id`에 해당하는 거래를 조회하며, 연관 물품/이미지 정보를 함께 반환합니다.
          
          ### 유의 사항
          - 날짜/시간 필드는 문자열로 반환되며 포맷은 `yyyy-MM-dd'T'HH:mm:ss`, 타임존은 Asia/Seoul입니다.
          """
  )
  ResponseEntity<TradeResponse> getTrade(
      UUID tradeId
  );

  @Operation(
      summary = "거래 목록 조회",
      description = """
          ### 요청 파라미터
          - `shopId` (UUID, optional): 해당 매장의 거래만 조회
          - `itemId` (UUID, optional): 해당 물품의 거래만 조회
          - `date` (LocalDate, optional, 형식 `yyyy-MM-dd`): 지정 시 **물품의 `pickupStartTime`**이 해당 날짜에 포함되는 거래만 조회
          - `tradeStatus` (TradeStatus, optional): 동일 상태의 거래만 조회
          - `pageNumber` (int, optional, 기본값 1): 페이지 번호
          - `pageSize` (int, optional, 기본값 `PageableConstants.DEFAULT_PAGE_SIZE`): 페이지 크기
          - `sortField` (TradeSortField, optional, 기본값 `CREATED_DATE`): 정렬 필드 (`CREATED_DATE`)
          - `sortDirection` (ASC | DESC, optional, 기본값 `DESC`): 정렬 방향
          
          ### 필터링/정렬 동작
          - 매장 필터: `TRADE.item.shop.shopId == shopId`
          - 물품 필터: `ITEM.itemId == itemId`
          - 날짜 필터: `Item.pickupStartTime`이 `date` 하루 범위(시작~끝, 포함)인 데이터만 조회합니다.
          - 상태 필터: `tradeStatus` 일치 조건으로 필터링합니다.
          
          ### 응답 데이터
          - `Page<TradeResponse>` 형태로 반환됩니다.
            - 각 요소의 스키마는 `TradeResponse`와 동일합니다(“거래 단건 조회”의 응답 데이터 참조).
            - 페이지 관련 메타데이터는 Spring Data `Page`의 기본 직렬화 결과를 따릅니다.
            
          ### 응답 데이터 (`TradeResponse`)
          - `tradeId` (UUID): 거래 ID
          - `tradeStatus` (TradeStatus): 거래 상태
          - `tradeQuantity` (int): 신청 수량
          - `itemId` (UUID): 물품 ID
          - `itemName` (String): 물품 이름
          - `itemImageUrls` (List<String>): 물품 이미지 URL 목록 (없으면 빈 배열)
          - `shopCategory` (ShopCategory): 매장 카테고리
          - `shopName` (String): 매장 이름
          - `latitude` (double): 매장 위도
          - `longitude` (double): 매장 경도
          - `siDo` (String): 주소(시/도)
          - `siGunGu` (String): 주소(시/군/구)
          - `eupMyoenDong` (String): 주소(읍/면/동)
          - `ri` (String): 주소(리)
          - `remainQuantity` (int): 현재 남은 수량
          - `expiredDate` (String, `yyyy-MM-dd'T'HH:mm:ss`, Asia/Seoul)
          - `pickupStartTime` (String, `yyyy-MM-dd'T'HH:mm:ss`, Asia/Seoul)
          - `pickupEndTime` (String, `yyyy-MM-dd'T'HH:mm:ss`, Asia/Seoul)
          - `pickupToday` (boolean): 수령 시작일이 오늘(Asia/Seoul)인지 여부
          
          ### 사용 방법
          - 필터가 필요 없으면 `date`, `tradeStatus`를 생략하고 페이징/정렬만으로 목록을 조회할 수 있습니다.
          """
  )
  ResponseEntity<Page<TradeResponse>> filteredTrade(
      TradeFilteredRequest request
  );
}
