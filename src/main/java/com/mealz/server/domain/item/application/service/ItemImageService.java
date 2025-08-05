package com.mealz.server.domain.item.application.service;

import com.mealz.server.domain.item.infrastructure.entity.Item;
import com.mealz.server.domain.item.infrastructure.entity.ItemImage;
import com.mealz.server.domain.item.infrastructure.repository.ItemImageRepository;
import com.mealz.server.domain.storage.core.constant.UploadType;
import com.mealz.server.domain.storage.core.service.StorageService;
import com.mealz.server.domain.storage.infrastructure.util.FileUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemImageService {

  private final ItemImageRepository itemImageRepository;
  private final StorageService storageService;

  public void saveImages(Item item, List<MultipartFile> files) {
    List<ItemImage> itemImages = new ArrayList<>();
    for (MultipartFile file : files) {
      FileUtil.isNullOrEmpty(file);
      String itemImageUrl = storageService.uploadFile(file, UploadType.ITEM);
      ItemImage itemImage = ItemImage.builder()
          .item(item)
          .itemImageUrl(itemImageUrl)
          .build();
      itemImages.add(itemImage);
    }
    itemImageRepository.saveAll(itemImages);
  }

  public List<ItemImage> getImagesByItem(Item item) {
    return itemImageRepository.findAllByItem(item);
  }
}
