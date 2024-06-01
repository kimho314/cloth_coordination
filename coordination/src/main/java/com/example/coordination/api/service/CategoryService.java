package com.example.coordination.api.service;

import com.example.coordination.api.dto.CategoryDto;
import com.example.coordination.api.dto.SaveCategoryDto;
import com.example.coordination.domain.repository.BrandRepository;
import com.example.coordination.domain.repository.CategoryRepository;
import com.example.coordination.domain.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final PriceRepository priceRepository;


    @Transactional
    public CategoryDto save(SaveCategoryDto categoryDto) {
        return null;
    }
}
