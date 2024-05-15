package com.example.coordination.api.controller;

import com.example.coordination.api.dto.AddBrandRequestDto;
import com.example.coordination.api.dto.AddCategoryRequestDto;
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
    public void addBrandName(@RequestBody AddBrandRequestDto request) {
        adminService.addBrandName(request.brandName());
    }

    @PutMapping("/category")
    public void addCategory(@RequestBody AddCategoryRequestDto request) {
        adminService.addCategory(request);
    }
}
