package com.lithanarianaren.notavito.dto.response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginResponse {
    private String token;
    private String email;
    private String role;

    public LoginResponse(String token, String email, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }
}
