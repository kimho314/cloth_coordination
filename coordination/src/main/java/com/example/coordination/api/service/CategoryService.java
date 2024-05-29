package com.example.coordination.api.service;

import com.example.coordination.api.dto.BrandPriceDto;
import com.example.coordination.api.dto.CategoryMinPriceDto;
import com.example.coordination.api.dto.GetCategoriesMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoryMinMaxPriceResponseDto;
import com.example.coordination.domain.entity.Category;
import com.example.coordination.domain.enums.CategoryType;
import com.example.coordination.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public GetCategoriesMinPriceResponseDto getCategoriesMinPrice() {
        AtomicLong totalPrice = new AtomicLong();
        List<CategoryMinPriceDto> categoryMinPriceDtos = new ArrayList<>();
        for (CategoryType elem : CategoryType.values()) {
            categoryRepository.findAllByCategoryType(elem).stream()
                    .sorted(Comparator.comparingLong(Category::getId).reversed())
                    .min(Comparator.comparing(Category::getPrice))
                    .ifPresent(it -> {
                        CategoryMinPriceDto minPriceDto = CategoryMinPriceDto.builder()
                                .brandName(it.getBrand().getName())
                                .price(it.getPrice())
                                .category(it.getCategoryType().getValue())
                                .build();
                        totalPrice.addAndGet(it.getPrice());
                        categoryMinPriceDtos.add(minPriceDto);
                    });
        }

        return new GetCategoriesMinPriceResponseDto(totalPrice.get(), categoryMinPriceDtos);
    }

    @Transactional(readOnly = true)
    public GetCategoryMinMaxPriceResponseDto getCategoryMinMaxPrice(CategoryType categoryType) {
        List<Category> categories = categoryRepository.findAllByCategoryType(categoryType).stream()
                .sorted(Comparator.comparingLong(Category::getId).reversed())
                .toList();

        AtomicReference<BrandPriceDto> minPrice = new AtomicReference<>();
        categories.stream()
                .min(Comparator.comparingInt(Category::getPrice))
                .ifPresent(it -> minPrice.set(BrandPriceDto.builder()
                        .brandName(it.getBrand().getName())
                        .price(it.getPrice())
                        .build()));

        AtomicReference<BrandPriceDto> maxPrice = new AtomicReference<>();
        categories.stream()
                .max(Comparator.comparingInt(Category::getPrice))
                .ifPresent(it -> maxPrice.set(BrandPriceDto.builder()
                        .brandName(it.getBrand().getName())
                        .price(it.getPrice())
                        .build()));

        return new GetCategoryMinMaxPriceResponseDto(categoryType, minPrice.get(), maxPrice.get());
    }
}
