package com.lithanarianaren.notavito.repository;

import com.lithanarianaren.notavito.entity.CategoryEntity;
import com.lithanarianaren.notavito.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findByParentId(Long parentId);

    List<CategoryEntity> findByNameContainingIgnoreCase(String name);


}
