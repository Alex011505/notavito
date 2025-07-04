package com.lithanarianaren.notavito.repository;


import com.lithanarianaren.notavito.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    @NonNull
    Optional<UserEntity> findByEmail(String email);

    @NonNull
    Optional<UserEntity> findById(@NonNull Long userId);

    Optional<UserEntity> findByPhone(String phone);

}