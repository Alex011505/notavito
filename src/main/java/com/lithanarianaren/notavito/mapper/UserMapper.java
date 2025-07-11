package com.lithanarianaren.notavito.mapper;

import com.lithanarianaren.notavito.dto.UserDto;
import com.lithanarianaren.notavito.dto.request.RegisterRequest;
import com.lithanarianaren.notavito.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapperUtil.class})
public interface UserMapper {

    UserDto toDto(UserEntity user);
    List<UserDto> toDtoList(List<UserEntity> users);


    @Mapping(target = "password", qualifiedByName = {"User", "hashPassword"}, source = "password")
    UserEntity fromCreateRequest(RegisterRequest registerRequest);
}
