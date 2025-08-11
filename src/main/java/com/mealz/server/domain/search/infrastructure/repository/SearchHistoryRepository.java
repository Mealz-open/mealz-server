package com.mealz.server.domain.search.infrastructure.repository;

import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.search.infrastructure.entity.SearchHistory;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, UUID> {

  void deleteByMemberAndKeyword(Member member, String keyword);

  List<SearchHistory> findTop30ByMemberOrderByCreatedDateDesc(Member member);
}
