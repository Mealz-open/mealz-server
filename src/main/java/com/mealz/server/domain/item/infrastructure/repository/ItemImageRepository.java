package com.mealz.server.domain.item.infrastructure.repository;

import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.item.infrastructure.entity.ItemImage;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, UUID> {

  List<ItemImage> findAllByItem(Item item);
}
