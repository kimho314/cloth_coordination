package com.example.coordination.api.controller;

import com.example.coordination.api.dto.BrandMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoriesMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoryMinMaxPriceResponseDto;
import com.example.coordination.api.service.CoordinationService;
import com.example.coordination.domain.enums.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class CoordinationController {
    private final CoordinationService coordinationService;

    @GetMapping("/categories/min-price")
    public ResponseEntity<GetCategoriesMinPriceResponseDto> getCategoriesMinPrice() {
        GetCategoriesMinPriceResponseDto response = coordinationService.getCategoriesMinPrice();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/brand/min-price")
    public ResponseEntity<BrandMinPriceResponseDto> getBrandMinPrice() {
        BrandMinPriceResponseDto response = coordinationService.getBrandMinPrice();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}/min-max-price")
    public ResponseEntity<GetCategoryMinMaxPriceResponseDto> getCategoryMinMaxPrice(@PathVariable String category) {
        GetCategoryMinMaxPriceResponseDto response = coordinationService.getCategoryMinMaxPrice(Category.findByValue(category).name());
        return ResponseEntity.ok(response);
    }

}
