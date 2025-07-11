package com.lithanarianaren.notavito.mapper;

import com.lithanarianaren.notavito.dto.AdvertisementDto;
import com.lithanarianaren.notavito.dto.request.AdvertisementRequest;
import com.lithanarianaren.notavito.entity.AdvertisementEntity;
import com.lithanarianaren.notavito.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ReferenceMapper.class, CategoryMapper.class})
public interface AdvertisementMapper{

    AdvertisementDto toDto(AdvertisementEntity advertisement);

    List<AdvertisementDto> toDtoList(List<AdvertisementEntity> advertisements);

    AdvertisementEntity fromCreateRequest(AdvertisementRequest request);

}
