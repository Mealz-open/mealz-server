package com.mealz.server.domain.shop.application.dto.request;

import com.mealz.server.domain.shop.core.constant.ShopCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopRequest {

  @NotBlank
  private String shopName;

  private ShopCategory shopCategory;

  private double longitude; // 경도

  private double latitude; // 위도

  @NotBlank
  private String siDo;

  @NotBlank
  private String siGunGu;

  @NotBlank
  private String eupMyoenDong;

  private String ri;
}
