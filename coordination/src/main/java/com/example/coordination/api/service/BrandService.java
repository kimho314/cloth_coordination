package com.example.coordination.api.service;

import com.example.coordination.api.dto.SaveBrandRequestDto;
import com.example.coordination.common.exception.DuplicateBrandException;
import com.example.coordination.domain.entity.Brand;
import com.example.coordination.domain.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;


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
}
