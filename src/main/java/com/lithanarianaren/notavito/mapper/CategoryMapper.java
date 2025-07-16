package com.lithanarianaren.notavito.mapper;

import com.lithanarianaren.notavito.dto.CategoryDto;
import com.lithanarianaren.notavito.dto.request.CategoryRequest;
import com.lithanarianaren.notavito.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ReferenceMapper.class})
public interface CategoryMapper {

    CategoryDto toDto(CategoryEntity category);

    List<CategoryDto> toDtoList(List<CategoryEntity> categories);

    CategoryEntity toEntity(Long id);

    CategoryEntity fromRequest(CategoryRequest request);
}
