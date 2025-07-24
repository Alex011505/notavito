package com.lithanarianaren.notavito.controller;


import com.lithanarianaren.notavito.dto.CategoryDto;
import com.lithanarianaren.notavito.dto.request.CategoryRequest;
import com.lithanarianaren.notavito.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryRequest categoryRequest) {
        logger.info("Creating category: {}", categoryRequest.getName());
        try {
            CategoryDto created = categoryService.create(categoryRequest);
            logger.info("Category {} created: {}", created.getId(), created.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            logger.error("Could not create category: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> getOrphanCategories() {
        logger.info("Looking up base categories");
        try {
            return ResponseEntity.ok(categoryService.findByParentId(null));
        } catch (RuntimeException e) {
            logger.error("Failed to look up base categories: {}.", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> editCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequest categoryRequest
    ) {
        logger.info("Editing category {}", id);
        try {
            CategoryDto updated = categoryService.edit(id, categoryRequest);
            return ResponseEntity.ok(updated);
        } catch (ResponseStatusException e) {
            logger.error("Failed to edit category {}: {} {}", id, e.getStatusCode(), e.getMessage());
            throw e;
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryDto>> searchByName(@RequestParam String name) {
        logger.info("Searching for categories: '{}'", name);
        try {
            List<CategoryDto> found = categoryService.findByNameContainingIgnoreCase(name);
            logger.info("Found {} categories with '{}'", found.size(), name);
            return ResponseEntity.ok(found);
        } catch (RuntimeException e) {
            logger.error("Failed to search for categories with '{}': {}", name, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
        logger.info("Searching for category {}", id);
        try {
            CategoryDto found = categoryService.getCategory(id);
            logger.info("Found category {}", id);
            return ResponseEntity.ok(found);
        } catch (ResponseStatusException e) {
            logger.error("Failed to search for category {}: {} {}",id, e.getStatusCode(), e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<List<CategoryDto>> findChildrenById(@PathVariable Long id) {
        logger.info("Searching for subcategories of {}", id);
        try {
            List<CategoryDto> found = categoryService.findByParentId(id);
            logger.info("Found {} subcategories of {}", found.size(), id);
            return ResponseEntity.ok(found);
        } catch (RuntimeException e) {
            logger.error("Failed to search for subcategories of {}: {}", id, e.getMessage());
            throw e;
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Deleting category {}", id);
        try {
            categoryService.deleteCategory(id);
            logger.info("Category {} deleted", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Failed to delete category {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
