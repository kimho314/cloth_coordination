package com.example.coordination.api.controller;

import com.example.coordination.api.dto.*;
import com.example.coordination.api.service.AdminService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    /**
     * @title 모든 브랜드의 상품 조회 API
     */
    @GetMapping("/goods")
    public ResponseEntity<GetGoodsResponseDto> getGoods() {
        GetGoodsResponseDto response = adminService.getGoods();
        return ResponseEntity.ok(response);
    }

    /**
     * @title 브랜드 추가 API
     */
    @PostMapping("/brand")
    public ResponseEntity<Void> addBrandName(@Valid @RequestBody BrandRequestDto request) {
        adminService.addBrandName(request);
        return ResponseEntity.ok().build();
    }

    /**
     * @title 브랜드 삭제 APi
     */
    @DeleteMapping("/brand/{brandName}")
    public ResponseEntity<Void> deleteBrandName(@PathVariable @NotBlank String brandName) {
        adminService.deleteBrandName(brandName);
        return ResponseEntity.ok().build();
    }

    /**
     * @title 카테고리 가격 추가 API
     */
    @PostMapping("/category")
    public ResponseEntity<Void> addCategory(@RequestBody @Valid AddCategoryRequestDto request) {
        adminService.addCategory(request);
        return ResponseEntity.ok().build();
    }

    /**
     * @title 카테고리 가격 수정 APi
     */
    @PutMapping("/category")
    public ResponseEntity<Void> modifyCategory(@RequestBody @Valid ModifyCategoryRequestDto request) {
        adminService.modifyCategory(request);
        return ResponseEntity.ok().build();
    }

    /**
     * @title 카테고리 가격 삭제 API
     */
    @DeleteMapping("/category")
    public ResponseEntity<Void> deleteCategory(@RequestBody @Valid DeleteCategoryRequestDto request) {
        adminService.deleteCategory(request);
        return ResponseEntity.ok().build();
    }

}
