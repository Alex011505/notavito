package com.lithanarianaren.notavito.repository;

import com.lithanarianaren.notavito.entity.AdvertisementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<AdvertisementEntity, Long> {

    List<AdvertisementEntity> findByTitleContainingIgnoreCase(String title);

}
