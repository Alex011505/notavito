package com.lithanarianaren.notavito.controller;

import com.lithanarianaren.notavito.dto.AdvertisementDto;
import com.lithanarianaren.notavito.dto.CategoryDto;
import com.lithanarianaren.notavito.dto.request.CategoryRequest;
import com.lithanarianaren.notavito.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryDto created = categoryService.create(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> getOrphanCategories() {
        return ResponseEntity.ok(categoryService.findByParentId(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> editCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequest categoryRequest
    ) {
        CategoryDto updated = categoryService.edit(id, categoryRequest);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryDto>> searchByName(@RequestParam String name) {
        List<CategoryDto> found = categoryService.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(found);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
        CategoryDto found = categoryService.getCategory(id);
        return ResponseEntity.ok(found);
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<List<CategoryDto>> findChildrenById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findByParentId(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
