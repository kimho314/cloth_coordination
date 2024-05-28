package com.example.coordination.api.service;

import com.example.coordination.api.dto.*;
import com.example.coordination.common.exception.CategoryFoundException;
import com.example.coordination.domain.entity.Goods;
import com.example.coordination.domain.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final GoodsRepository goodsRepository;


    @Transactional
    public void addBrandName(BrandRequestDto request) {
        isDuplicatedBrandName(request);
        List<Goods> goodsList = mappedToGoods(request);

        goodsRepository.saveAll(goodsList);
    }

    private void isDuplicatedBrandName(BrandRequestDto request) {
        List<Goods> goods = goodsRepository.findAllByBrandName(request.brandName());
        if (!goods.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private static List<Goods> mappedToGoods(BrandRequestDto request) {
        return request.categoryPriceDtos().stream()
                .map(it -> Goods.builder()
                        .brandName(request.brandName())
                        .categoryType(it.categoryType())
                        .price(it.price() == null ? 0 : it.price())
                        .build())
                .toList();
    }

    @Transactional
    public void modifyCategory(ModifyCategoryRequestDto request) {
        Goods goods = goodsRepository.findByBrandNameAndCategory(request.brandName(), request.categoryType())
                .orElseThrow(() -> new NoSuchElementException(request.brandName()));

        goods.setPrice(request.price());
        goodsRepository.save(goods);
    }

    @Transactional(readOnly = true)
    public GetGoodsResponseDto getGoods() {
        List<Goods> goods = goodsRepository.findAll();
        Map<String, List<GoodsDto>> goodsMap = mappedToGoodsMap(goods);

        return new GetGoodsResponseDto(goodsMap);
    }

    private static Map<String, List<GoodsDto>> mappedToGoodsMap(List<Goods> goods) {
        return goods.stream()
                .map(it -> GoodsDto.builder()
                        .brandName(it.getBrandName())
                        .categoryType(it.getCategoryType())
                        .price(it.getPrice())
                        .build())
                .collect(Collectors.groupingBy(GoodsDto::brandName));
    }

    @Transactional
    public void deleteBrandName(String brandName) {
        goodsRepository.deleteAllByBrandName(brandName);
    }

    @Transactional
    public void deleteCategory(DeleteCategoryRequestDto request) {
        Goods goods = goodsRepository.findByBrandNameAndCategory(request.brandName(), request.categoryType())
                .orElseThrow(() -> new NoSuchElementException(request.brandName() + " " + request.categoryType()));

        goods.setPrice(null);
    }

    @Transactional
    public void addCategory(AddCategoryRequestDto request) {
        Goods goods = goodsRepository.findByBrandNameAndCategory(request.brandName(), request.categoryType())
                .orElseThrow(NoSuchElementException::new);
        if (!Objects.isNull(goods.getPrice())) {
            throw new CategoryFoundException(request.categoryType().getValue());
        }

        goods.setPrice(request.price());
        goodsRepository.save(goods);
    }
}
