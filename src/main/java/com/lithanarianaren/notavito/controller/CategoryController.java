package com.lithanarianaren.notavito.controller;

import com.lithanarianaren.notavito.dto.CategoryDto;
import com.lithanarianaren.notavito.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nodes")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createNode(@RequestBody CategoryDto nodeDto) {
        CategoryDto created = categoryService.saveCategory(nodeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateNode(
            @PathVariable Long id,
            @RequestBody CategoryDto updatedDto
    ) {
        updatedDto.setId(id);
        CategoryDto updated = categoryService.saveCategory(updatedDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryDto>> searchByName(@RequestParam String name) {
        List<CategoryDto> found = categoryService.findByName(name);
        return ResponseEntity.ok(found);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(@RequestParam Long id) {
        CategoryDto found = categoryService.getCategory(id);
        return ResponseEntity.ok(found);
    }

    // 🗑️ Удаление
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
