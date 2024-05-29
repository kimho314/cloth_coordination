package com.example.coordination.api.controller;

import com.example.coordination.api.dto.BrandMinPriceResponseDto;
import com.example.coordination.api.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping("/min-price")
    public ResponseEntity<BrandMinPriceResponseDto> getBrandMinPrice() {
        BrandMinPriceResponseDto response = brandService.getBrandMinPrice();
        return ResponseEntity.ok(response);
    }
}
