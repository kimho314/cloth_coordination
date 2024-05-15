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

    /**
     * @return
     * @title 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     */
    @GetMapping("/categories/min-price")
    public ResponseEntity<GetCategoriesMinPriceResponseDto> getCategoriesMinPrice() {
        GetCategoriesMinPriceResponseDto response = coordinationService.getCategoriesMinPrice();
        return ResponseEntity.ok(response);
    }

    /**
     * @return
     * @title 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을
     * 조회하는 API
     */
    @GetMapping("/brand/min-price")
    public ResponseEntity<BrandMinPriceResponseDto> getBrandMinPrice() {
        BrandMinPriceResponseDto response = coordinationService.getBrandMinPrice();
        return ResponseEntity.ok(response);
    }

    /**
     * @param category
     * @return
     * @title 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
     */
    @GetMapping("/category/{category}/min-max-price")
    public ResponseEntity<GetCategoryMinMaxPriceResponseDto> getCategoryMinMaxPrice(@PathVariable String category) {
        GetCategoryMinMaxPriceResponseDto response = coordinationService.getCategoryMinMaxPrice(Category.findByValue(category).name());
        return ResponseEntity.ok(response);
    }

}
