package com.mealz.server.domain.member.application.dto.response;

import java.util.UUID;
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
public class MemberInfoResponse {

  // 회원 PK
  private UUID memberId;

  // 이메일
  private String username;

  // 닉네임
  private String nickname;

  // 이름
  private String name;

  // 생일
  private String birthDay;

  // 출생연도
  private String birthYear;

  // 전화번호
  private String phone;

  // 프로필 이미지
  private String profileUrl;

  // 성별
  private String gender;
}
