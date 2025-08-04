package com.mealz.server.domain.shop.application.dto.request;

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

  private long latitude;

  private long longitude;

  @NotBlank
  private String siDo;

  @NotBlank
  private String siGunGu;

  @NotBlank
  private String eupMyoenDong;

  private String ri;
}
