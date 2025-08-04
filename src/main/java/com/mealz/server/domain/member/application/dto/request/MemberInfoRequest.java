package com.mealz.server.domain.member.application.dto.request;

import com.mealz.server.domain.member.core.constant.MemberType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class MemberInfoRequest {

  @Size(min = 3, max = 10, message = "닉네임은 3글자 이상 10이하로 설정하세요")
  @NotNull(message = "닉네임을 입력하세요")
  private String nickname;

  @NotNull(message = "기부자/수혜자 정보를 입력하세요")
  private MemberType memberType;

  private MultipartFile profileImage;
}
