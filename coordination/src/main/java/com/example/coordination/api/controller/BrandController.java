package com.example.coordination.api.controller;

import com.example.coordination.api.dto.AddBrandRequestDto;
import com.example.coordination.api.dto.BrandMinPriceResponseDto;
import com.example.coordination.api.dto.GetBrandsResponseDto;
import com.example.coordination.api.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    public ResponseEntity<Void> saveBrand(@RequestBody @Valid AddBrandRequestDto addBrandRequestDto) {
        brandService.save(addBrandRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<GetBrandsResponseDto> getBrands() {
        GetBrandsResponseDto response = brandService.getBrands();
        return ResponseEntity.ok(response);
    }
}
