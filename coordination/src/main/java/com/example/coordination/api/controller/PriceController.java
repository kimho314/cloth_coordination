package com.example.coordination.api.controller;

import com.example.coordination.api.dto.BrandMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoriesMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoryMinMaxPriceResponseDto;
import com.example.coordination.api.service.PriceService;
import com.example.coordination.domain.enums.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/prices")
@RestController
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @GetMapping("/min-prices")
    public ResponseEntity<GetCategoriesMinPriceResponseDto> getMinPrices() {
        GetCategoriesMinPriceResponseDto categoriesMinPrice = priceService.getCategoriesMinPrice();
        return ResponseEntity.ok(categoriesMinPrice);
    }

    @GetMapping("/brand/min-price")
    public ResponseEntity<BrandMinPriceResponseDto> getBrandMinPrice() {
        BrandMinPriceResponseDto response = priceService.getBrandMinPrice();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories/{category}/min-max-price")
    public ResponseEntity<GetCategoryMinMaxPriceResponseDto> getCategoryMinMaxPrice(@PathVariable String category) {
        GetCategoryMinMaxPriceResponseDto response = priceService.getCategoryMinMaxPrice(CategoryType.findByValue(category));
        return ResponseEntity.ok(response);
    }
}
