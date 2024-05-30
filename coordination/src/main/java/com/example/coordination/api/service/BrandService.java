package com.example.coordination.api.service;

import com.example.coordination.api.dto.AddBrandRequestDto;
import com.example.coordination.api.dto.BrandMinPriceResponseDto;
import com.example.coordination.api.dto.CategoryPriceDto;
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
    public void save(AddBrandRequestDto addBrandRequestDto) {
        Optional<Brand> maybeBrand = brandRepository.findByName(addBrandRequestDto.brandName());
        if (maybeBrand.isPresent()) {
            throw new DuplicateBrandException(addBrandRequestDto.brandName());
        }

        Brand brand = Brand.builder()
                .name(addBrandRequestDto.brandName())
                .build();
        brandRepository.save(brand);
    }
}
