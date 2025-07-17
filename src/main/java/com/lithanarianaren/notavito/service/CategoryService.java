package com.lithanarianaren.notavito.service;


import com.lithanarianaren.notavito.dto.CategoryDto;
import com.lithanarianaren.notavito.dto.request.CategoryRequest;
import com.lithanarianaren.notavito.entity.CategoryEntity;
import com.lithanarianaren.notavito.mapper.AdvertisementMapper;
import com.lithanarianaren.notavito.mapper.CategoryMapper;
import com.lithanarianaren.notavito.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AdvertisementMapper advertisementMapper;

    public CategoryDto create(CategoryRequest categoryRequest) {
        CategoryEntity category = categoryMapper.fromRequest(categoryRequest);

        categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    public CategoryDto edit(Long id, CategoryRequest categoryRequest) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "No such category"));

        categoryMapper.updateFromRequest(categoryRequest, category);

        categoryRepository.save(category);

        return categoryMapper.toDto(category);

    }

    public CategoryDto getCategory(Long id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(()-> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No such category"
            ));

        return categoryMapper.toDto(category);
    }

    public Optional<CategoryEntity> getCategoryEntity(Long id) {
        return categoryRepository.findById(id);
    }

    public List<CategoryDto> findByNameContainingIgnoreCase(String name) {
        return categoryMapper.toDtoList(categoryRepository.findByNameContainingIgnoreCase(name));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


    public List<CategoryDto> findByParentId(Long parentId) {
        return categoryMapper.toDtoList(categoryRepository.findByParentId(parentId));
    }


}
