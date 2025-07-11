package com.lithanarianaren.notavito.service;

import com.lithanarianaren.notavito.dto.UserDto;
import com.lithanarianaren.notavito.dto.request.LoginRequest;
import com.lithanarianaren.notavito.dto.request.RegisterRequest;
import com.lithanarianaren.notavito.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;




    public UserDto register(RegisterRequest request){

        ensureUnique(request);

        UserEntity user = userMapper.fromCreateRequest(request);
        userRepository.save(user);

        return userMapper.toDto(user);

    }

    private void ensureUnique(RegisterRequest request){

        Map<String, String> err = new HashMap<>();


        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            err.put("email", "Email in use");
        }

        if (userRepository.findByPhone(request.getPhone()).isPresent()){

            err.put("numPhone", "Phone in use");

        }

        if(!err.isEmpty())
            throw new ResponseStatusException(

                    HttpStatus.BAD_REQUEST,
                    String.join(", ", err.values())

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
        if (authentication != null && authentication.isAuthenticated()) {
            String login = authentication.getName();
            return Optional.ofNullable(findByEmail(login));
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

