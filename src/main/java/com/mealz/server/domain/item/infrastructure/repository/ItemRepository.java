package com.mealz.server.domain.item.infrastructure.repository;

import com.mealz.server.domain.item.infrastructure.entity.Item;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, UUID> {

}
