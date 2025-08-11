package com.mealz.server.domain.shop.infrastructure.repository;

import com.mealz.server.domain.member.infrastructure.entity.Member;
import com.mealz.server.domain.shop.infrastructure.entity.Shop;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, UUID> {

  List<Shop> findByMember(Member member);

  List<Shop> findTop30ByOrderByDonateCountDesc();
}
