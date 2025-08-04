package com.mealz.server.domain.member.infrastructure.repository;

import com.mealz.server.domain.member.core.constant.Role;
import com.mealz.server.domain.member.infrastructure.entity.Member;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, UUID> {

  Optional<Member> findByUsername(String username);

  void deleteAllByRole(Role role);
}
