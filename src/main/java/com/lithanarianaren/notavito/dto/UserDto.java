package com.lithanarianaren.notavito.dto;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lithanarianaren.notavito.entity.AdvertisementEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String phone;

}