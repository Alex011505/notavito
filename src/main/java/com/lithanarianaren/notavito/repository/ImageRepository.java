package com.lithanarianaren.notavito.repository;

import com.lithanarianaren.notavito.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    Optional<ImageEntity> findByStringId(String stringId);
    boolean existsByStringId(String stringId);
}

