package com.lithanarianaren.notavito.service;

import com.lithanarianaren.notavito.dto.CategoryDto;
import com.lithanarianaren.notavito.entity.CategoryEntity;
import com.lithanarianaren.notavito.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto saveCategory(CategoryDto dto) {
        CategoryEntity category = new CategoryEntity();
        category.setName(dto.getName());

        if (dto.getParentId() != null) {
            category.setParent(categoryRepository.findById(dto.getParentId()).orElse(null));
        }

        CategoryEntity saved = categoryRepository.save(category);

        dto.setId(saved.getId());
        return dto;
    }

    public List<CategoryDto> getChildren(Long parentId) {
        return categoryRepository.findByParentId(parentId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDto> getCategory(Long id) {
        return categoryRepository.findById(id)
                .map(this::toDto);
    }

    public Optional<CategoryEntity> getCategoryEntity(Long id) {
        return categoryRepository.findById(id);
    }

    private CategoryDto toDto(CategoryEntity category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setParentId(category.getParent() != null ? category.getParent().getId() : null);
        return dto;
    }

    public List<CategoryDto> findByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


}
