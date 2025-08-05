package com.mealz.server.domain.shop.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mealz.server.domain.shop.core.constant.ShopCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopRequest {

  @NotBlank
  private String shopName;

  private ShopCategory shopCategory;

  private MultipartFile shopImage;

  private String shopDescription;

  private double longitude; // 경도

  private double latitude; // 위도

  @NotBlank
  private String siDo;

  @NotBlank
  private String siGunGu;

  @NotBlank
  private String eupMyoenDong;

  private String ri;

  @Pattern(
      regexp = "\\d{2,3}-\\d{3,4}-\\d{4}",
      message = "전화번호 형식이 올바르지 않습니다. (예: 02-1234-5678)"
  )
  private String shopPhoneNumber;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
  private LocalTime openTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
  private LocalTime closeTime;
}
