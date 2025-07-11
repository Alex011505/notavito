package com.lithanarianaren.notavito.mapper;

import com.lithanarianaren.notavito.dto.CategoryDto;
import com.lithanarianaren.notavito.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ReferenceMapper.class})
public interface CategoryMapper {
    //@Mapping(source = "parent", target = "parent", qualifiedByName = {"Mapper","getId"})
    public CategoryDto toDto(CategoryEntity category);

    public List<CategoryDto> toDtoList(List<CategoryEntity> categories);

    CategoryEntity toEntity(Long id);
}
