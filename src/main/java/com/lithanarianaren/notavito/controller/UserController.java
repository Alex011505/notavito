package com.lithanarianaren.notavito.controller;

import com.lithanarianaren.notavito.dto.request.LoginRequest;
import com.lithanarianaren.notavito.dto.request.RegisterRequest;
import com.lithanarianaren.notavito.entity.UserEntity;
import com.lithanarianaren.notavito.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserEntity> register(@Valid @RequestBody RegisterRequest request){

        UserEntity user = service.register(request);
        return ResponseEntity.ok(user);

    }

    @PostMapping("/login")
    public ResponseEntity<UserEntity> login(@RequestBody LoginRequest request){

        UserEntity user = service.login(request);
        return ResponseEntity.ok(user);

    }

    @GetMapping
    public ResponseEntity<UserEntity> getUser(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone
    ) {
        Optional<UserEntity> user = service.findUser(id, email, phone);

        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void delete(@RequestParam Long id){

        service.delete(id);

    }

}