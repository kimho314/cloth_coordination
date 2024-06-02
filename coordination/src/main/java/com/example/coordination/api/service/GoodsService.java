package com.example.coordination.api.service;

import com.example.coordination.api.dto.*;
import com.example.coordination.common.exception.NoBrandException;
import com.example.coordination.common.exception.NoCategoryException;
import com.example.coordination.domain.dto.MinBrandDto;
import com.example.coordination.domain.dto.MinCategoryDto;
import com.example.coordination.domain.dto.MinMaxCategoryDto;
import com.example.coordination.domain.entity.Brand;
import com.example.coordination.domain.entity.Category;
import com.example.coordination.domain.entity.Goods;
import com.example.coordination.domain.enums.CategoryType;
import com.example.coordination.domain.repository.BrandRepository;
import com.example.coordination.domain.repository.CategoryRepository;
import com.example.coordination.domain.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public GetCategoriesMinPriceResponseDto getCategoriesMinPrice() {
        List<MinCategoryDto> minCategoryPrices = goodsRepository.findMinCategoryPrices();

        List<CategoryMinPriceDto> categoryMinPriceDtos = new ArrayList<>();
        for (MinCategoryDto elem : minCategoryPrices) {
            Optional<Goods> maybePrice = goodsRepository.findFirstByCategory_IdAndAmountOrderByIdDesc(elem.categoryId(), elem.minAmount());
            if (maybePrice.isPresent()) {
                Goods goods = maybePrice.get();

                categoryMinPriceDtos.add(
                        CategoryMinPriceDto.builder()
                                .brandName(goods.getBrand().getName())
                                .category(goods.getCategory().getCategoryType().getValue())
                                .price(goods.getAmount())
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
        Optional<MinBrandDto> maybeMinBrandPrice = goodsRepository.findFirstMinBrandPrice();
        if (maybeMinBrandPrice.isEmpty()) {
            return null;
        }

        MinBrandDto minBrandDto = maybeMinBrandPrice.get();

        List<Goods> goods = goodsRepository.findAllByBrand_Id(minBrandDto.brandId());
        Brand brand = brandRepository.findById(minBrandDto.brandId())
                .orElseThrow(NoSuchElementException::new);

        return new BrandMinPriceResponseDto(brand.getName(), getCategoryPriceDtos(goods), minBrandDto.sum());
    }

    private static List<CategoryPriceDto> getCategoryPriceDtos(List<Goods> goods) {
        return goods.stream()
                .map(it -> CategoryPriceDto.builder()
                        .price(it.getAmount())
                        .categoryType(it.getCategory().getCategoryType())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public GetCategoryMinMaxPriceResponseDto getCategoryMinMaxPrice(CategoryType categoryType) {
        Category category = categoryRepository.findByCategoryType(categoryType)
                .orElseThrow(NoCategoryException::new);

        Optional<MinMaxCategoryDto> maybeMinMaxPrice = goodsRepository.findMinMaxCategoryByCategoryId(category.getId());
        if (maybeMinMaxPrice.isEmpty()) {
            return null;
        }

        MinMaxCategoryDto minMaxCategoryDto = maybeMinMaxPrice.get();
        BrandPriceDto minPrice = getMinPrice(category, minMaxCategoryDto);
        BrandPriceDto maxPrice = getMaxPrice(category, minMaxCategoryDto);

        return new GetCategoryMinMaxPriceResponseDto(categoryType, minPrice, maxPrice);
    }

    private BrandPriceDto getMaxPrice(Category category, MinMaxCategoryDto minMaxCategoryDto) {
        return goodsRepository.findFirstByCategory_IdAndAmountOrderByIdDesc(category.getId(), minMaxCategoryDto.maxAmount())
                .stream()
                .findFirst()
                .map(it -> BrandPriceDto.builder()
                        .brandName(it.getBrand().getName())
                        .price(it.getAmount())
                        .build())
                .orElseThrow(NoSuchElementException::new);
    }

    private BrandPriceDto getMinPrice(Category category, MinMaxCategoryDto minMaxCategoryDto) {
        return goodsRepository.findFirstByCategory_IdAndAmountOrderByIdDesc(category.getId(), minMaxCategoryDto.minAmount())
                .stream()
                .findFirst()
                .map(it -> BrandPriceDto.builder()
                        .brandName(it.getBrand().getName())
                        .price(it.getAmount())
                        .build())
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void save(SaveGoodsRequestDto request) {
        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(NoBrandException::new);

        List<Goods> newGoods = new ArrayList<>();
        for (GoodsDto elem : request.goodsDtos()) {
            Optional<Category> maybeCategory = categoryRepository.findByCategoryType(elem.categoryType());
            if (maybeCategory.isPresent()) {
                Category category = maybeCategory.get();
                newGoods.add(
                        Goods.builder()
                                .category(category)
                                .brand(brand)
                                .amount(elem.amount())
                                .build()
                );
            }
        }
        goodsRepository.saveAll(newGoods);
        brand.setGoods(newGoods);
    }
}
