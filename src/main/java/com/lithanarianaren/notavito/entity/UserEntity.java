package com.lithanarianaren.notavito.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String patronymic;
    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //@JsonManagedReference
    //private List<AdvertisementEntity> advertisements;


}

