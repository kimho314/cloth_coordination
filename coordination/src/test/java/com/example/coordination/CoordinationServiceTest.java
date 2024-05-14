package com.example.coordination;

import com.example.coordination.api.dto.BrandMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoriesMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoryMinMaxPriceResponseDto;
import com.example.coordination.api.service.CoordinationService;
import com.example.coordination.domain.enums.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CoordinationServiceTest {
    @Autowired
    CoordinationService coordinationService;

    @Test
    void getCategoriesMinPriceTest() {
        GetCategoriesMinPriceResponseDto categoriesMinPrice = coordinationService.getCategoriesMinPrice();
        System.out.println(categoriesMinPrice.totalPrice());
        categoriesMinPrice.categoryMinPriceDtos().forEach(it -> System.out.println(it));
    }

    @Test
    void getBrandMinPriceTest() {
        BrandMinPriceResponseDto brandMinPrice = coordinationService.getBrandMinPrice();
        System.out.println(brandMinPrice);
    }

    @Test
    void getCategoryMinMaxPriceTest() {
        final String category = Category.TOPS.name();
        GetCategoryMinMaxPriceResponseDto categoryMinMaxPrice = coordinationService.getCategoryMinMaxPrice(category);
        System.out.println(categoryMinMaxPrice);
    }
}
