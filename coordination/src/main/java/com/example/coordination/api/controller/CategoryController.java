package com.example.coordination.api.controller;

import com.example.coordination.api.dto.SaveCategoryDto;
import com.example.coordination.api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<Void> saveCategory(@RequestBody @Valid SaveCategoryDto categoryDto) {
        categoryService.save(categoryDto);
        return ResponseEntity.ok().build();
    }
}
