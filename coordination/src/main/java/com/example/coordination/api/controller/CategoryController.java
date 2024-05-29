package com.example.coordination.api.controller;

import com.example.coordination.api.dto.GetCategoriesMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoryMinMaxPriceResponseDto;
import com.example.coordination.api.service.CategoryService;
import com.example.coordination.domain.enums.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/min-prices")
    public ResponseEntity<GetCategoriesMinPriceResponseDto> getMinPrices() {
        GetCategoriesMinPriceResponseDto categoriesMinPrice = categoryService.getCategoriesMinPrice();
        return ResponseEntity.ok(categoriesMinPrice);
    }

    @GetMapping("/{category}/min-max-price")
    public ResponseEntity<GetCategoryMinMaxPriceResponseDto> getCategoryMinMaxPrice(@PathVariable String category) {
        GetCategoryMinMaxPriceResponseDto response = categoryService.getCategoryMinMaxPrice(CategoryType.findByValue(category));
        return ResponseEntity.ok(response);
    }
}
