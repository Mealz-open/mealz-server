package com.mealz.server.domain.search.application.service;

import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.search.infrastructure.entity.SearchHistory;
import com.mealz.server.domain.search.infrastructure.repository.SearchHistoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchHistoryService {

  private final SearchHistoryRepository searchHistoryRepository;

  @Transactional
  public void saveMemberSearchHistory(Member member, String keyword) {
    searchHistoryRepository.deleteByMemberAndKeyword(member, keyword);
    SearchHistory searchHistory = SearchHistory.builder()
        .member(member)
        .keyword(keyword)
        .build();
    searchHistoryRepository.save(searchHistory);
  }

  @Transactional
  public List<String> getMemberRecentKeyword(Member member) {
    return searchHistoryRepository.findTop30ByMemberOrderByCreatedDateDesc(member)
        .stream()
        .map(SearchHistory::getKeyword)
        .collect(Collectors.toList());
  }

}
