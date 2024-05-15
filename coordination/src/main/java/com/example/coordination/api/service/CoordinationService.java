package com.example.coordination.api.service;

import com.example.coordination.api.dto.*;
import com.example.coordination.domain.dto.GetBrandSumImpl;
import com.example.coordination.domain.dto.GetCategoriesMinPriceImpl;
import com.example.coordination.domain.dto.GetCategoryMinMaxPriceImpl;
import com.example.coordination.domain.entity.Goods;
import com.example.coordination.domain.enums.Category;
import com.example.coordination.domain.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoordinationService {
    private final GoodsRepository goodsRepository;

    @Transactional(readOnly = true)
    public GetCategoriesMinPriceResponseDto getCategoriesMinPrice() {
        List<GetCategoriesMinPriceImpl> categoryMinPrices = goodsRepository.getCategoryMinPrices();
        List<CategoryMinPriceDto> result = getCategoryMinPriceDtos(categoryMinPrices);

        long totalPrice = getCategoriesTotalPrice(result);

        return new GetCategoriesMinPriceResponseDto(totalPrice, result);
    }

    private static long getCategoriesTotalPrice(List<CategoryMinPriceDto> result) {
        return result.stream()
                .mapToLong(CategoryMinPriceDto::price)
                .sum();
    }

    private static List<CategoryMinPriceDto> getCategoryMinPriceDtos(List<GetCategoriesMinPriceImpl> categoryMinPrices) {
        return categoryMinPrices.stream()
                .map(it -> CategoryMinPriceDto.builder()
                        .brandName(it.getBrandName())
                        .category(it.getCategory())
                        .price(it.getPrice())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BrandMinPriceResponseDto getBrandMinPrice() {
        Optional<GetBrandSumImpl> brandSumsOptional = goodsRepository.getBrandSums();
        if (brandSumsOptional.isEmpty()) {
            return null;
        }
        GetBrandSumImpl minBrandSum = brandSumsOptional.get();

        List<Goods> goods = goodsRepository.findAllByBrandName(minBrandSum.getBrandName());
        List<CategoryPriceDto> categoryPriceDtos = getCategoryPriceDtos(goods);

        return new BrandMinPriceResponseDto(minBrandSum.getBrandName(), categoryPriceDtos, minBrandSum.getSumPrice());
    }

    private static List<CategoryPriceDto> getCategoryPriceDtos(List<Goods> goods) {
        return goods.stream()
                .map(it -> CategoryPriceDto.builder()
                        .category(it.getCategory())
                        .price(it.getPrice())
                        .build())
                .toList();
    }


    @Transactional(readOnly = true)
    public GetCategoryMinMaxPriceResponseDto getCategoryMinMaxPrice(String category) {
        List<GetCategoryMinMaxPriceImpl> categoryMinMaxPrices = goodsRepository.getCategoryMinMaxPrices(category);
        if (categoryMinMaxPrices.isEmpty()) {
            return GetCategoryMinMaxPriceResponseDto.createDefault();
        }

        return new GetCategoryMinMaxPriceResponseDto(Category.findByName(category), getMinBrandPriceDto(categoryMinMaxPrices), getMaxBrandPriceDto(categoryMinMaxPrices));
    }

    private static BrandPriceDto getMaxBrandPriceDto(List<GetCategoryMinMaxPriceImpl> categoryMinMaxPrices) {
        return getBrandPriceDto(categoryMinMaxPrices, 1);
    }

    private static BrandPriceDto getMinBrandPriceDto(List<GetCategoryMinMaxPriceImpl> categoryMinMaxPrices) {
        return getBrandPriceDto(categoryMinMaxPrices, 0);
    }

    private static BrandPriceDto getBrandPriceDto(List<GetCategoryMinMaxPriceImpl> categoryMinMaxPrices, int index) {
        GetCategoryMinMaxPriceImpl min = categoryMinMaxPrices.get(index);
        return BrandPriceDto.builder()
                .brandName(min.getBrandName())
                .price(min.getPrice())
                .build();
    }
}
