package com.lithanarianaren.notavito.repository;

import com.lithanarianaren.notavito.entity.AdvertisementEntity;
import com.lithanarianaren.notavito.entity.CategoryEntity;
import com.lithanarianaren.notavito.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, Long> {

    List<AdvertisementEntity> findByTitleContainingIgnoreCase(String title);



}
