package com.mealz.server.domain.item.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequest {

  @NotNull(message = "물품을 등록할 매장의 식별자를 입력하세요")
  private UUID shopId;

  @NotBlank(message = "물품 이름을 입력하세요")
  private String itemName;

  @Min(1)
  private int quantity;

  private List<MultipartFile> itemImages;

  @NotNull(message = "유통/소비기한을 입력하세요")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime expiredDate;

  @NotNull(message = "픽업 시작 시간을 입력하세요. (yyyy-MM-dd'T'HH:mm:ss)")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime pickupStartDate;

  @NotNull(message = "픽업 종료 시간을 입력하세요. (yyyy-MM-dd'T'HH:mm:ss)")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime pickupEndDate;
}
