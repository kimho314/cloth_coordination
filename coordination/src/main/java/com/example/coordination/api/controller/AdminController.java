package com.example.coordination.api.controller;

import com.example.coordination.api.dto.AddCategoryRequestDto;
import com.example.coordination.api.dto.BrandRequestDto;
import com.example.coordination.api.dto.GetGoodsResponseDto;
import com.example.coordination.api.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/goods")
    public ResponseEntity<GetGoodsResponseDto> getGoods() {
        GetGoodsResponseDto response = adminService.getGoods();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/brand")
    public ResponseEntity<Void> addBrandName(@RequestBody BrandRequestDto request) {
        adminService.addBrandName(request.brandName());
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/brand/{brandName}")
    public ResponseEntity<Void> deleteBrandName(@PathVariable String brandName) {
        adminService.deleteBrandName(brandName);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/category")
    public ResponseEntity<Void> modifyCategory(@RequestBody AddCategoryRequestDto request) {
        adminService.addCategory(request);
        return ResponseEntity.ok(null);
    }

}
