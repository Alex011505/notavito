package com.lithanarianaren.notavito.service;

import com.lithanarianaren.notavito.dto.request.LoginRequest;
import com.lithanarianaren.notavito.dto.request.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.lithanarianaren.notavito.entity.UserEntity;
import com.lithanarianaren.notavito.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;




    public UserEntity register(RegisterRequest request){

        ensureUnique(request);

        UserEntity user = new UserEntity();

        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPatronymic(request.getPatronymic());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        userRepository.save(user);

        Optional<UserEntity> newUser = userRepository.findByEmail(request.getEmail());
        if(newUser.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return newUser.get();

    }

    private void ensureUnique(RegisterRequest request){

        Map<String, String> err = new HashMap<>();


        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            err.put("email", "Email in use");
        }

        if (userRepository.findByPhone(request.getPhone()).isPresent()){

            err.put("numPhone", "Phone in use");

        }

            throw new ResponseStatusException(

                    HttpStatus.BAD_REQUEST,
                    String.join(", ", err.values())

            );

        }



    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String login = authentication.getName();
            return findByEmail(login);
        }
        return Optional.empty();
    }

    public void delete(Long id){

        userRepository.deleteById(id);

    }

    public UserEntity login(LoginRequest request){

        Optional<UserEntity> user = userRepository.findByEmail(request.getEmail());
        Map<String, String> err = new HashMap<>();
        if (user.isPresent()){
            UserEntity sureUser = user.get();
            if (sureUser.getPassword().equals(request.getPassword())){
                return sureUser;
            } else {
                err.put("password", "Incorrect password");
            }
        } else {
            err.put("email", "No such email");
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                String.join(", ", err.values())
        );

    }


    public Optional<UserEntity> findUser(Long id, String email, String phone) {
        if (id != null) {
            return userRepository.findById(id);
        } else if (email != null) {
            return userRepository.findByEmail(email);
        } else if (phone != null) {
            return userRepository.findByPhone(phone);
        } else {
            return Optional.empty();
        }
    }

}

