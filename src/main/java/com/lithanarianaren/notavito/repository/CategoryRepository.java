package com.lithanarianaren.notavito.repository;

import com.lithanarianaren.notavito.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findByParentId(Long parentId);

    List<CategoryEntity> findByNameContainingIgnoreCase(String name);

    //List<AdvertisementEntity> getAdvertisements(Long id);


}
