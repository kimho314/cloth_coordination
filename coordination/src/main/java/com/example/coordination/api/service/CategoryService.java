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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public GetCategoriesMinPriceResponseDto getCategoriesMinPrice() {
        long totalPrice = 0;
        List<CategoryMinPriceDto> categoryMinPriceDtos = new ArrayList<>();
        for (CategoryType elem : CategoryType.values()) {
            Optional<Category> maybeMin = categoryRepository.findAllByCategoryType(elem).stream()
                    .sorted(Comparator.comparingLong(Category::getId).reversed())
                    .min(Comparator.comparing(Category::getPrice));

            if (maybeMin.isPresent()) {
                Category min = maybeMin.get();
                totalPrice += min.getPrice();
                CategoryMinPriceDto minPriceDto = CategoryMinPriceDto.builder()
                        .brandName(min.getBrand().getName())
                        .price(min.getPrice())
                        .category(min.getCategoryType().getValue())
                        .build();
                categoryMinPriceDtos.add(minPriceDto);
            }
        }

        return new GetCategoriesMinPriceResponseDto(totalPrice, categoryMinPriceDtos);
    }

    @Transactional(readOnly = true)
    public GetCategoryMinMaxPriceResponseDto getCategoryMinMaxPrice(CategoryType categoryType) {
        List<Category> categories = categoryRepository.findAllByCategoryType(categoryType).stream()
                .sorted(Comparator.comparingLong(Category::getId).reversed())
                .toList();

        BrandPriceDto minPrice = getMinPrice(categories);
        BrandPriceDto maxPrice = getMaxPrice(categories);

        return new GetCategoryMinMaxPriceResponseDto(categoryType, minPrice, maxPrice);
    }

    private static BrandPriceDto getMaxPrice(List<Category> categories) {
        BrandPriceDto maxPrice = null;
        Optional<Category> maybeMax = categories.stream()
                .max(Comparator.comparingInt(Category::getPrice));
        if (maybeMax.isPresent()) {
            Category max = maybeMax.get();
            maxPrice = BrandPriceDto.builder()
                    .brandName(max.getBrand().getName())
                    .price(max.getPrice())
                    .build();
        }
        return maxPrice;
    }

    private static BrandPriceDto getMinPrice(List<Category> categories) {
        BrandPriceDto minPrice = null;
        Optional<Category> maybeMin = categories.stream()
                .min(Comparator.comparingInt(Category::getPrice));

        if (maybeMin.isPresent()) {
            Category min = maybeMin.get();
            minPrice = BrandPriceDto.builder()
                    .brandName(min.getBrand().getName())
                    .price(min.getPrice())
                    .build();
        }
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
