package com.lithanarianaren.notavito.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Cannot be blank")

    @NotBlank(message = "Cannot be blank")
    @Pattern(regexp = "^[А-ЯЁа-яё]+$", message = "Can only use cyrillic letters")

    private String name;

    @NotBlank(message = "Cannot be blank")
    @Pattern(regexp = "^[А-ЯЁа-яё]+(-[А-ЯЁа-яё]+)?$", message = "Can only use cyrillic letters or a hyphen")
    private String surname;

    @Pattern(regexp = "^$|^[А-ЯЁа-яё]$", message = "Can only use cyrillic letters, may be blank")
    private String patronymic;

    @NotBlank(message = "Cannot be blank")
    @Size(min=6, message = "Cannot be shorter than 6 symbols")
    private String password;

    @NotBlank(message = "Cannot be blank")
    @Email(message = "Incorrect email format")
    private String email;

    @Pattern(regexp = "^$|^\\+7\\d{10}$", message = "Phone format: +7__________")
    private String phone;

}
