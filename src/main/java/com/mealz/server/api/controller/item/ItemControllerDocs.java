package com.mealz.server.api.controller.item;

import com.mealz.server.domain.auth.infrastructure.oauth2.CustomOAuth2User;
import com.mealz.server.domain.item.application.dto.request.ItemRequest;
import com.mealz.server.domain.item.application.dto.response.ItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

public interface ItemControllerDocs {

  @Operation(
      summary = "물품 등록",
      description = """
          ### 요청 파라미터
          - `shopId` (UUID, required): 등록할 매장의 식별자
          - `itemName` (String, required): 물품 이름
          - `quantity` (int, required): 수량
          - `itemImages` (List<MultipartFile>, optional): 물품 이미지 파일 (multipart/form-data, 다중 전송 가능)
          - `expiredDate` (LocalDateTime, required): 유통/소비 기한 (`yyyy-MM-dd'T'HH:mm:ss`)
          - `pickupStartDate` (LocalDateTime, required): 픽업 시작 시간 (`yyyy-MM-dd'T'HH:mm:ss`)
          - `pickupEndDate` (LocalDateTime, required): 픽업 종료 시간 (`yyyy-MM-dd'T'HH:mm:ss`)
          
          ### 응답 데이터
          - 없음 (Void): 성공 시 빈 응답 반환 (HTTP 200 OK)
          
          ### 사용 방법
          1. DONATOR 권한을 가진 사용자가 인증 후 호출합니다.  
          2. 요청 헤더에 `Content-Type: multipart/form-data`를 설정합니다.  
          3. 각 필드를 form-data로 전송하며, 이미지 파일은 `itemImages` 키로 여러 개 업로드할 수 있습니다.  
          
          ### 유의 사항
          - 날짜/시간 필드(`expiredDate`, `pickupStartDate`, `pickupEndDate`)는 반드시 ISO 8601 형식이어야 합니다.  
          - `quantity`는 음수가 될 수 없으며, 최솟값은 1입니다.  
          - `shopId`는 요청 사용자가 소유한 매장의 UUID여야 합니다.  
          """
  )
  ResponseEntity<Void> createItem(
      CustomOAuth2User customOAuth2User,
      ItemRequest request
  );

  @Operation(
      summary = "물품 조회",
      description = """
          ### 요청 파라미터
          - `item-id` (UUID, required, path): 조회할 물품의 식별자
          
          ### 응답 데이터
          ```json
          {
            "itemName": "String",
            "itemImageUrls": ["String", ...],
            "shopCategory": "ShopCategory",
            "shopName": "String",
            "latitude": 37.123456,
            "longitude": 127.123456,
            "siDo": "String",
            "siGunGu": "String",
            "eupMyoenDong": "String",
            "ri": "String",
            "quantity": 1,
            "expiredDate": "2025-08-05T15:30:00",
            "pickupStartTime": "2025-08-05T16:00:00",
            "pickupEndTime": "2025-08-05T18:00:00"
          }
          ```
          
          ### 사용 방법
          1. GET 메서드로 `/api/item/{item-id}` 엔드포인트를 호출합니다.  
          2. `item-id` 경로 변수에 조회할 물품의 UUID를 입력합니다.  
          3. 성공 시 위 형식의 JSON을 HTTP 200 OK로 반환합니다.  
          
          ### 유의 사항
          - `item-id`는 UUID 형식이어야 하며, 올바르지 않을 경우 요청이 거부될 수 있습니다.  
          """
  )
  ResponseEntity<ItemResponse> getItem(
      UUID itemId
  );
}
