package com.example.coordination.api.service;

import com.example.coordination.api.dto.AddCategoryRequestDto;
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
    public void addBrandName(String brandName) {
        List<Goods> goodsList = Goods.createDefaultWithBrandName(brandName);
        goodsRepository.saveAll(goodsList);
    }

    @Transactional
    public void addCategory(AddCategoryRequestDto request) {
        Goods goods = goodsRepository.findByBrandNameAndCategory(request.brandName(), request.category())
                .orElseThrow(() -> new NoSuchElementException(request.brandName()));

        goods.setPrice(request.price());
    }

    @Transactional(readOnly = true)
    public GetGoodsResponseDto getGoods() {
        Map<String, List<GoodsDto>> goodsMap = goodsRepository.findAll().stream()
                .map(it -> GoodsDto.builder()
                        .brandName(it.getBrandName())
                        .category(it.getCategory())
                        .price(it.getPrice())
                        .build())
                .collect(Collectors.groupingBy(it -> it.brandName()));
        return new GetGoodsResponseDto(goodsMap);
    }

    @Transactional
    public void deleteBrandName(String brandName) {
        goodsRepository.deleteAllByBrandName(brandName);
    }
}
