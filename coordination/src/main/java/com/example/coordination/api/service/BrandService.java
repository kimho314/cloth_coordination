package com.example.coordination.api.service;

import com.example.coordination.api.dto.*;
import com.example.coordination.common.exception.DuplicateBrandException;
import com.example.coordination.domain.entity.Brand;
import com.example.coordination.domain.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public BrandMinPriceResponseDto getBrandMinPrice() {
        Brand minBrand = brandRepository.findAll().stream()
                .min(Comparator.comparing(Brand::getPriceSum))
                .orElseThrow(NoSuchElementException::new);

        return new BrandMinPriceResponseDto(minBrand.getName(), getCategoryPriceDtos(minBrand), (long) minBrand.getPriceSum());
    }

    private static List<CategoryPriceDto> getCategoryPriceDtos(Brand minBrand) {
        return minBrand.getCategories().stream()
                .map(it -> CategoryPriceDto.builder()
                        .categoryType(it.getCategoryType())
                        .price(it.getPrice())
                        .build())
                .toList();
    }

    @Transactional
    public void save(SaveBrandRequestDto saveBrandRequestDto) {
        Optional<Brand> maybeBrand = brandRepository.findByName(saveBrandRequestDto.brandName());
        if (maybeBrand.isPresent()) {
            throw new DuplicateBrandException(saveBrandRequestDto.brandName());
        }

        Brand brand = Brand.builder()
                .name(saveBrandRequestDto.brandName())
                .build();
        brandRepository.save(brand);
    }

    @Transactional(readOnly = true)
    public GetBrandsResponseDto getBrands() {
        List<BrandDto> brandDtos = brandRepository.findAll().stream()
                .map(it -> BrandDto.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .createdAt(it.getCreatedAt())
                        .categoryDtos(it.getCategories().stream()
                                .map(category -> CategoryDto.builder()
                                        .id(category.getId())
                                        .price(category.getPrice())
                                        .categoryType(category.getCategoryType())
                                        .createdAt(category.getCreatedAt())
                                        .build())
                                .toList())
                        .build())
                .toList();

        return new GetBrandsResponseDto(brandDtos);
    }
}
