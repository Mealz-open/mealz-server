package com.mealz.server.domain.member.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberType {
  DONATOR("기부자"),
  BENEFICIARY("수혜자");

  private final String description;
}
