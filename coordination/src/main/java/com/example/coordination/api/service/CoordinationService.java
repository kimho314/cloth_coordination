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

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoordinationService {
    private final GoodsRepository goodsRepository;

    @Transactional(readOnly = true)
    public GetCategoriesMinPriceResponseDto getCategoriesMinPrice() {
        List<GetCategoriesMinPriceImpl> categoryMinPrices = goodsRepository.getCategoryMinPrices();
        List<CategoryMinPriceDto> result = categoryMinPrices.stream()
                .map(it -> CategoryMinPriceDto.builder()
                        .brandName(it.getBrandName())
                        .category(it.getCategory())
                        .price(it.getPrice())
                        .build())
                .collect(Collectors.toList());

        long totalPrice = result.stream()
                .mapToLong(it -> it.price())
                .sum();

        return new GetCategoriesMinPriceResponseDto(totalPrice, result);
    }

    @Transactional(readOnly = true)
    public BrandMinPriceResponseDto getBrandMinPrice() {
        List<GetBrandSumImpl> list = goodsRepository.getBrandSums().stream().toList();
        if (list.isEmpty()) {
            return BrandMinPriceResponseDto.createDefault();
        }

        PriorityQueue<GetBrandSumImpl> pq = new PriorityQueue<>(Comparator.comparing(it -> it.getSumPrice()));
        pq.addAll(list);
        GetBrandSumImpl minBrandSum = pq.poll();

        assert minBrandSum != null;
        List<Goods> goods = goodsRepository.findAllByBrandName(minBrandSum.getBrandName());
        List<CategoryPriceDto> categoryPriceDtos = goods.stream()
                .map(it -> CategoryPriceDto.builder()
                        .category(it.getCategory())
                        .price(it.getPrice())
                        .build())
                .toList();

        return new BrandMinPriceResponseDto(minBrandSum.getBrandName(), categoryPriceDtos, minBrandSum.getSumPrice());
    }

    @Transactional(readOnly = true)
    public GetCategoryMinMaxPriceResponseDto getCategoryMinMaxPrice(String category) {
        List<GetCategoryMinMaxPriceImpl> categoryMinMaxPrices = goodsRepository.getCategoryMinMaxPrices(category);
        if (categoryMinMaxPrices.isEmpty()) {
            return GetCategoryMinMaxPriceResponseDto.createDefault();
        }

        GetCategoryMinMaxPriceImpl min = categoryMinMaxPrices.get(0);
        BrandPriceDto minBrandPriceDto = BrandPriceDto.builder()
                .brandName(min.getBrandName())
                .price(min.getPrice())
                .build();
        GetCategoryMinMaxPriceImpl max = categoryMinMaxPrices.get(1);
        BrandPriceDto maxBrandPriceDto = BrandPriceDto.builder()
                .brandName(max.getBrandName())
                .price(max.getPrice())
                .build();

        return new GetCategoryMinMaxPriceResponseDto(Category.findByName(category), minBrandPriceDto, maxBrandPriceDto);
    }
}
