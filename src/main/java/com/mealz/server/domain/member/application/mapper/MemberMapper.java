package com.mealz.server.domain.member.application.mapper;

import com.mealz.server.domain.member.application.dto.response.MemberInfoResponse;
import com.mealz.server.domain.member.infrastructure.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

  // Member -> MemberInfoResponse (DTO)
  MemberInfoResponse toMemberInfoResponse(Member member);

}
