package com.lithanarianaren.notavito.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lithanarianaren.notavito.enum_.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
public class UserEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column
    private String patronymic;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.USER;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AdvertisementEntity> advertisements;


}

