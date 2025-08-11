package com.mealz.server.domain.search.infrastructure.repository;

import com.mealz.server.domain.search.infrastructure.entity.Search;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<Search, UUID> {

  Optional<Search> findByKeyword(String keyword);

  List<Search> findTop30ByOrderByCountDesc();
}
