package com.mealz.server.domain.mock.application.dto.request;

import com.mealz.server.domain.member.core.constant.AccountStatus;
import com.mealz.server.domain.member.core.constant.Role;
import com.mealz.server.domain.member.core.constant.SocialPlatform;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {

  @Email
  private String username;

  private Role role;

  private SocialPlatform socialPlatform;

  private AccountStatus accountStatus;

  private Boolean isFirstLogin;
}
