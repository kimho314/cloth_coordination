package com.example.coordination.api.controller;

import com.example.coordination.api.dto.BrandMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoriesMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoryMinMaxPriceResponseDto;
import com.example.coordination.api.dto.SaveGoodsRequestDto;
import com.example.coordination.api.service.GoodsService;
import com.example.coordination.domain.enums.CategoryType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/goods")
@RestController
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    @GetMapping("/min-prices")
    public ResponseEntity<GetCategoriesMinPriceResponseDto> getMinPrices() {
        GetCategoriesMinPriceResponseDto categoriesMinPrice = goodsService.getCategoriesMinPrice();
        return ResponseEntity.ok(categoriesMinPrice);
    }

    @GetMapping("/brand/min-price")
    public ResponseEntity<BrandMinPriceResponseDto> getBrandMinPrice() {
        BrandMinPriceResponseDto response = goodsService.getBrandMinPrice();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories/{category}/min-max-price")
    public ResponseEntity<GetCategoryMinMaxPriceResponseDto> getCategoryMinMaxPrice(@PathVariable String category) {
        GetCategoryMinMaxPriceResponseDto response = goodsService.getCategoryMinMaxPrice(CategoryType.findByValue(category));
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<Void> saveGoods(@RequestBody @Valid SaveGoodsRequestDto request) {
        goodsService.save(request);
        return ResponseEntity.ok().build();
    }
}
