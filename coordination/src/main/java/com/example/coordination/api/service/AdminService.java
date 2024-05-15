package com.example.coordination.api.service;

import com.example.coordination.api.dto.AddCategoryRequestDto;
import com.example.coordination.api.dto.BrandRequestDto;
import com.example.coordination.api.dto.GetGoodsResponseDto;
import com.example.coordination.api.dto.GoodsDto;
import com.example.coordination.domain.entity.Goods;
import com.example.coordination.domain.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
                        .category(it.category())
                        .price(it.price() == null ? 0 : it.price())
                        .build())
                .toList();
    }

    @Transactional
    public void addCategory(AddCategoryRequestDto request) {
        Goods goods = goodsRepository.findByBrandNameAndCategory(request.brandName(), request.category())
                .orElseThrow(() -> new NoSuchElementException(request.brandName()));

        goods.setPrice(request.price());
    }

    @Transactional(readOnly = true)
    public GetGoodsResponseDto mappedToGoods() {
        List<Goods> goods = goodsRepository.findAll();
        Map<String, List<GoodsDto>> goodsMap = amppedToGoodsMap(goods);

        return new GetGoodsResponseDto(goodsMap);
    }

    private static Map<String, List<GoodsDto>> amppedToGoodsMap(List<Goods> goods) {
        return goods.stream()
                .map(it -> GoodsDto.builder()
                        .brandName(it.getBrandName())
                        .category(it.getCategory())
                        .price(it.getPrice())
                        .build())
                .collect(Collectors.groupingBy(GoodsDto::brandName));
    }

    @Transactional
    public void deleteBrandName(String brandName) {
        goodsRepository.deleteAllByBrandName(brandName);
    }
}
