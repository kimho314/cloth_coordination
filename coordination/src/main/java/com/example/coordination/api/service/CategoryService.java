package com.example.coordination.api.service;

import com.example.coordination.api.dto.*;
import com.example.coordination.common.exception.CategoryFoundException;
import com.example.coordination.common.exception.NoBrandException;
import com.example.coordination.domain.entity.Brand;
import com.example.coordination.domain.entity.Category;
import com.example.coordination.domain.enums.CategoryType;
import com.example.coordination.domain.repository.BrandRepository;
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
    private final BrandRepository brandRepository;
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

        AtomicReference<BrandPriceDto> minPrice = getMinPrice(categories);
        AtomicReference<BrandPriceDto> maxPrice = getMaxPrice(categories);

        return new GetCategoryMinMaxPriceResponseDto(categoryType, minPrice.get(), maxPrice.get());
    }

    private static AtomicReference<BrandPriceDto> getMaxPrice(List<Category> categories) {
        AtomicReference<BrandPriceDto> maxPrice = new AtomicReference<>();
        categories.stream()
                .max(Comparator.comparingInt(Category::getPrice))
                .ifPresent(it -> maxPrice.set(BrandPriceDto.builder()
                        .brandName(it.getBrand().getName())
                        .price(it.getPrice())
                        .build()));
        return maxPrice;
    }

    private static AtomicReference<BrandPriceDto> getMinPrice(List<Category> categories) {
        AtomicReference<BrandPriceDto> minPrice = new AtomicReference<>();
        categories.stream()
                .min(Comparator.comparingInt(Category::getPrice))
                .ifPresent(it -> minPrice.set(BrandPriceDto.builder()
                        .brandName(it.getBrand().getName())
                        .price(it.getPrice())
                        .build()));
        return minPrice;
    }

    @Transactional
    public CategoryDto save(SaveCategoryDto categoryDto) {
        Brand brand = brandRepository.findById(categoryDto.brandId())
                .orElseThrow(NoBrandException::new);

        if (categoryRepository.existsByCategoryTypeAndBrand_Id(categoryDto.categoryType(), categoryDto.brandId())) {
            throw new CategoryFoundException(categoryDto.categoryType().getValue());
        }

        Category category = Category.builder()
                .brand(brand)
                .categoryType(categoryDto.categoryType())
                .price(categoryDto.price())
                .build();
        Category saved = categoryRepository.save(category);

        return CategoryDto.builder()
                .id(saved.getId())
                .categoryType(saved.getCategoryType())
                .price(saved.getPrice())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
