package com.mealz.server.domain.member.application.dto.request;

import com.mealz.server.domain.member.core.constant.MemberType;
import jakarta.validation.constraints.NotNull;
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
public class MemberInfoRequest {

  @NotNull(message = "기부자/수혜자 정보를 입력하세요")
  private MemberType memberType;
}
