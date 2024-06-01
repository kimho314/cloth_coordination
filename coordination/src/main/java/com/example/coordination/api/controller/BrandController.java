package com.example.coordination.api.controller;

import com.example.coordination.api.dto.SaveBrandRequestDto;
import com.example.coordination.api.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;


    @PostMapping("")
    public ResponseEntity<Void> saveBrand(@RequestBody @Valid SaveBrandRequestDto saveBrandRequestDto) {
        brandService.save(saveBrandRequestDto);
        return ResponseEntity.ok().build();
    }
}
