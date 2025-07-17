package com.lithanarianaren.notavito.mapper;

import com.lithanarianaren.notavito.dto.ImageDto;
import com.lithanarianaren.notavito.dto.response.ImageDataResponse;
import com.lithanarianaren.notavito.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ReferenceMapper.class, ImageMapperUtil.class})
public interface ImageMapper {

    default ImageDto toDto(final ImageEntity image) {
        return ImageDto.builder().url(ImageMapperUtil.buildUrl(image)).build();
    }

    List<ImageDto> toDtoList(List<ImageEntity> categories);

    ImageEntity toEntity(Long id);

    ImageDataResponse toDataResponse(ImageEntity image);

}
