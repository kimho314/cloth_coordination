package com.example.coordination.api.service;

import com.example.coordination.api.dto.BrandMinPriceResponseDto;
import com.example.coordination.api.dto.CategoryPriceDto;
import com.example.coordination.domain.entity.Brand;
import com.example.coordination.domain.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public BrandMinPriceResponseDto getBrandMinPrice() {
        Brand minBrand = brandRepository.findAll().stream()
                .min(Comparator.comparing(Brand::getPriceSum))
                .orElseThrow(NoSuchElementException::new);

        String brandName = minBrand.getName();
        List<CategoryPriceDto> categoryPriceDtos = minBrand.getCategories().stream()
                .map(it -> CategoryPriceDto.builder()
                        .categoryType(it.getCategoryType())
                        .price(it.getPrice())
                        .build())
                .toList();
        long totalPrice = minBrand.getPriceSum();

        return new BrandMinPriceResponseDto(brandName, categoryPriceDtos, totalPrice);
    }
}
