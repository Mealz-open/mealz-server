package com.mealz.server.domain.member.application.service;

import com.mealz.server.domain.member.application.dto.request.MemberInfoRequest;
import com.mealz.server.domain.member.application.dto.response.MemberInfoResponse;
import com.mealz.server.domain.member.application.mapper.MemberMapper;
import com.mealz.server.domain.member.core.constant.MemberType;
import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.member.infrastructure.repository.MemberRepository;
import com.mealz.server.global.exception.CustomException;
import com.mealz.server.global.exception.ErrorCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberRepository memberRepository;
  private final MemberMapper memberMapper;

  /**
   * JWT 기반 회원정보 조회
   *
   * @param member @AuthenticationPrincipal 을 통한 인증된 회원 정보
   */
  @Transactional(readOnly = true)
  public MemberInfoResponse getMemberInfo(Member member) {
    return memberMapper.toMemberInfoResponse(member);
  }

  /**
   * 회원 정보 설정
   * @param request
   */
  @Transactional
  public void setMemberInfo(Member member, MemberInfoRequest request) {
    member.setMemberType(request.getMemberType());
  }

  /**
   * memberId에 해당하는 회원 반환
   *
   * @param memberId 회원 PK
   */
  public Member findMemberById(UUID memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> {
          log.error("요청한 PK값에 해당하는 회원을 찾을 수 없습니다. 요청 PK: {}", memberId);
          return new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        });
  }

  public boolean isValidMemberType(Member member, MemberType memberType) {
    return member.getMemberType().equals(memberType);
  }
}
