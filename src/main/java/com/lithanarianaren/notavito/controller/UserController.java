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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService service;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request){
        logger.info("Registering user {}", request.getEmail());
        try {
            LoginResponse response = service.register(request);
            logger.info("User {} registered", response.getEmail());
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            logger.error("User {} failed to register: {} {}", request.getEmail(), e.getStatusCode(), e.getMessage());
            throw e;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        logger.info("User {} logging in", request.getEmail());
        try {
            LoginResponse response = service.login(request);
            logger.info("{} {} logged in", response.getRole(), response.getEmail());
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            logger.error("User {} failed to log in: {} {}", request.getEmail(), e.getStatusCode(), e.getMessage());
            throw e;
        }

    }

    @GetMapping("/search")
    public ResponseEntity<UserDto> getUser(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone
    ) {
        logger.info("Searching for user ({},{},{})", id, email, phone);
        try {
            Optional<UserDto> user = service.findUser(id, email, phone);
            return user.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            logger.error("Failed to search for user ({},{},{}): {}", id, email, phone, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        logger.info("Looking for me.");
        try {
            Optional<UserDto> user = service.getCurrentUser();
            if(user.isPresent()) {
                logger.info("I am {}.", user.get().getEmail());
                return ResponseEntity.ok(user.get());
            } else{
                logger.info("I am unauthorized.");
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            logger.error("I am lost: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        logger.info("Looking for all users");
        try {
            List<UserDto> users = service.findAllUsers();
            logger.info("Found {} users", users.size());
            return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            logger.error("Cannot get all users: {}", e.getMessage());
            throw e;
        }
    }

    @DeleteMapping
    public void delete(@RequestParam Long id){
        logger.info("Deleting user {}", id);
        try {
            service.delete(id);
            logger.info("User {} deleted", id);
        } catch (ResponseStatusException e) {
            logger.error("Cannot delete user {}: {} {}", id, e.getStatusCode(), e.getMessage());
            throw e;
        }

    }

}