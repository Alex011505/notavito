package com.lithanarianaren.notavito.controller;

import com.lithanarianaren.notavito.dto.UserDto;
import com.lithanarianaren.notavito.dto.request.LoginRequest;
import com.lithanarianaren.notavito.dto.request.RegisterRequest;
import com.lithanarianaren.notavito.dto.response.LoginResponse;
import com.lithanarianaren.notavito.entity.UserEntity;
import com.lithanarianaren.notavito.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request){

        LoginResponse response = service.register(request);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){

        LoginResponse response = service.login(request);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/search")
    public ResponseEntity<UserDto> getUser(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone
    ) {
        Optional<UserDto> user = service.findUser(id, email, phone);

        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        Optional<UserDto> user = service.getCurrentUser();
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(service.findAllUsers());
    }

    @DeleteMapping
    public void delete(@RequestParam Long id){

        service.delete(id);

    }

}