package com.lithanarianaren.notavito.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Fill in the email")
    @Email(message = "Incorrect email format")
    private String email;
    @NotBlank(message = "Fill in the password")
    private String password;

}