package com.lithanarianaren.notavito.service;

import com.lithanarianaren.notavito.dto.UserDto;
import com.lithanarianaren.notavito.dto.request.LoginRequest;
import com.lithanarianaren.notavito.dto.request.RegisterRequest;
import com.lithanarianaren.notavito.dto.response.LoginResponse;
import com.lithanarianaren.notavito.mapper.UserMapper;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.lithanarianaren.notavito.entity.UserEntity;
import com.lithanarianaren.notavito.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JSONWebTokenService jsonWebTokenService;
    private final PasswordEncoder passwordEncoder;



    private LoginResponse makeLoginResponse(UserEntity user) {
        String token = jsonWebTokenService.newToken(user.getEmail(), user.getRole().toString());

        return new LoginResponse(token, user.getEmail(), user.getRole().toString());
    }

    public LoginResponse register(RegisterRequest request){

        ensureUnique(request);

        UserEntity user = userMapper.fromCreateRequest(request);
        userRepository.save(user);

        return makeLoginResponse(user);

    }

    private void ensureUnique(RegisterRequest request){

        List<String> err = new ArrayList<>();


        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            err.add("Email in use: %s".formatted(request.getEmail()));
        }

        if (userRepository.findByPhone(request.getPhone()).isPresent()){

            err.add("Phone in use: %s".formatted(request.getPhone()));

        }

        if(!err.isEmpty())
            throw new ResponseStatusException(

                    HttpStatus.BAD_REQUEST,
                    String.join(", ", err)

            );

        }

    public UserDto findByEmail(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No such user"
            );
        }
        return userMapper.toDto(optionalUser.get());
    }

    public List<UserDto> findAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    public Optional<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        if (authentication != null && authentication.isAuthenticated()) {
            String login = authentication.getName();
            System.out.println(login);
            return Optional.ofNullable(findByEmail(login));
        }
        return Optional.empty();
    }

    public void delete(Long id){

        userRepository.deleteById(id);

    }

    public LoginResponse login(LoginRequest request){

        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such email"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
        }

        return makeLoginResponse(user);

    }


    public Optional<UserDto> findUser(Long id, String email, String phone) {
        if (id != null) {
            return userRepository.findById(id).map(userMapper::toDto);
        } else if (email != null) {
            return userRepository.findByEmail(email).map(userMapper::toDto);
        } else if (phone != null) {
            return userRepository.findByPhone(phone).map(userMapper::toDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getEmail())
                    .password(userObj.getPassword())
                    .build();
        }else{
            throw new UsernameNotFoundException(email);
        }
    }
}

