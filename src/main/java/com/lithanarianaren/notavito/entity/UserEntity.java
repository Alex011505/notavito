package com.lithanarianaren.notavito.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Setter
public class UserEntity extends BaseEntity {

    private String name;
    private String surname;
    private String patronymic;
    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AdvertisementEntity> advertisements;


}

