package com.lithanarianaren.notavito.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String phone;
    private String role;
}