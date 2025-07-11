package com.lithanarianaren.notavito.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Named("User")
@Component
@RequiredArgsConstructor
public class UserMapperUtil {
    private final PasswordEncoder passwordEncoder;

    @Named("hashPassword")
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
