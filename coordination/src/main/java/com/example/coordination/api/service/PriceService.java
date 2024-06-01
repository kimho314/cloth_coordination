package com.example.coordination.api.service;

import com.example.coordination.api.dto.*;
import com.example.coordination.common.exception.NoCategoryException;
import com.example.coordination.domain.dto.MinBrandDto;
import com.example.coordination.domain.dto.MinCategoryDto;
import com.example.coordination.domain.dto.MinMaxCategoryDto;
import com.example.coordination.domain.entity.Brand;
import com.example.coordination.domain.entity.Category;
import com.example.coordination.domain.entity.Price;
import com.example.coordination.domain.enums.CategoryType;
import com.example.coordination.domain.repository.BrandRepository;
import com.example.coordination.domain.repository.CategoryRepository;
import com.example.coordination.domain.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final PriceRepository priceRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public GetCategoriesMinPriceResponseDto getCategoriesMinPrice() {
        List<MinCategoryDto> minCategoryPrices = priceRepository.findMinCategoryPrices();

        List<CategoryMinPriceDto> categoryMinPriceDtos = new ArrayList<>();
        for (MinCategoryDto elem : minCategoryPrices) {
            Optional<Price> maybePrice = priceRepository.findFirstByCategory_IdAndAmountOrderByIdDesc(elem.categoryId(), elem.minAmount());
            if (maybePrice.isPresent()) {
                Price price = maybePrice.get();

                categoryMinPriceDtos.add(
                        CategoryMinPriceDto.builder()
                                .brandName(price.getBrand().getName())
                                .category(price.getCategory().getCategoryType().getValue())
                                .price(price.getAmount())
                                .build()
                );
            }
        }

        return new GetCategoriesMinPriceResponseDto(getSum(minCategoryPrices), categoryMinPriceDtos);
    }

    private static long getSum(List<MinCategoryDto> minCategoryPrices) {
        return minCategoryPrices.stream().mapToLong(MinCategoryDto::minAmount).sum();
    }

    @Transactional(readOnly = true)
    public BrandMinPriceResponseDto getBrandMinPrice() {
        Optional<MinBrandDto> maybeMinBrandPrice = priceRepository.findFirstMinBrandPrice();
        if (maybeMinBrandPrice.isEmpty()) {
            return null;
        }

        MinBrandDto minBrandDto = maybeMinBrandPrice.get();
        List<Price> prices = priceRepository.findAllByBrand_Id(minBrandDto.brandId());
        Brand brand = brandRepository.findById(minBrandDto.brandId())
                .orElseThrow(NoSuchElementException::new);

        List<CategoryPriceDto> categoryPriceDtos = prices.stream()
                .map(it -> CategoryPriceDto.builder()
                        .price(it.getAmount())
                        .categoryType(it.getCategory().getCategoryType())
                        .build())
                .toList();

        return new BrandMinPriceResponseDto(brand.getName(), categoryPriceDtos, minBrandDto.sum());
    }

    @Transactional(readOnly = true)
    public GetCategoryMinMaxPriceResponseDto getCategoryMinMaxPrice(CategoryType categoryType) {
        Category category = categoryRepository.findByCategoryType(categoryType)
                .orElseThrow(NoCategoryException::new);

        Optional<MinMaxCategoryDto> maybeMinMaxPrice = priceRepository.findMinMaxCategoryByCategoryId(category.getId());
        if (maybeMinMaxPrice.isEmpty()) {
            return null;
        }

        MinMaxCategoryDto minMaxCategoryDto = maybeMinMaxPrice.get();
        BrandPriceDto minPrice = getMinPrice(category, minMaxCategoryDto);
        BrandPriceDto maxPrice = getMaxPrice(category, minMaxCategoryDto);

        return new GetCategoryMinMaxPriceResponseDto(categoryType, minPrice, maxPrice);
    }

    private BrandPriceDto getMaxPrice(Category category, MinMaxCategoryDto minMaxCategoryDto) {
        return priceRepository.findFirstByCategory_IdAndAmountOrderByIdDesc(category.getId(), minMaxCategoryDto.maxAmount())
                .stream()
                .findFirst()
                .map(it -> BrandPriceDto.builder()
                        .brandName(it.getBrand().getName())
                        .price(it.getAmount())
                        .build())
                .orElseThrow(NoSuchElementException::new);
    }

    private BrandPriceDto getMinPrice(Category category, MinMaxCategoryDto minMaxCategoryDto) {
        return priceRepository.findFirstByCategory_IdAndAmountOrderByIdDesc(category.getId(), minMaxCategoryDto.minAmount())
                .stream()
                .findFirst()
                .map(it -> BrandPriceDto.builder()
                        .brandName(it.getBrand().getName())
                        .price(it.getAmount())
                        .build())
                .orElseThrow(NoSuchElementException::new);
    }
}
