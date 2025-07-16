package com.lithanarianaren.notavito.mapper;

import com.lithanarianaren.notavito.dto.ImageDto;
import com.lithanarianaren.notavito.dto.response.ImageDataResponse;
import com.lithanarianaren.notavito.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ReferenceMapper.class, ImageMapperUtil.class})
public abstract class ImageMapper {

    public ImageDto toDto(final ImageEntity image) {
        return ImageDto.builder().url(ImageMapperUtil.buildUrl(image)).build();
    }

    public abstract List<ImageDto> toDtoList(List<ImageEntity> categories);

    public abstract ImageEntity toEntity(Long id);

    public abstract ImageDataResponse toDataResponse(ImageEntity image);

}
