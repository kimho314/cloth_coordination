package com.example.coordination.api.service;

import com.example.coordination.api.dto.SaveCategoryDto;
import com.example.coordination.common.exception.NoBrandException;
import com.example.coordination.common.exception.NoCategoryException;
import com.example.coordination.domain.entity.Brand;
import com.example.coordination.domain.entity.Category;
import com.example.coordination.domain.entity.Goods;
import com.example.coordination.domain.repository.BrandRepository;
import com.example.coordination.domain.repository.CategoryRepository;
import com.example.coordination.domain.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final GoodsRepository goodsRepository;


    @Transactional
    public void save(SaveCategoryDto categoryDto) {
        Optional<Brand> maybeBrand = brandRepository.findById(categoryDto.brandId());
        if (maybeBrand.isEmpty()) {
            throw new NoBrandException();
        }

        Optional<Category> maybeCategory = categoryRepository.findByCategoryType(categoryDto.categoryType());
        if (maybeCategory.isEmpty()) {
            throw new NoCategoryException();
        }

        Brand brand = maybeBrand.get();
        Category category = maybeCategory.get();

        Goods goods = Goods.builder()
                .amount(categoryDto.price())
                .category(category)
                .brand(brand)
                .build();
        goodsRepository.save(goods);
    }
}
