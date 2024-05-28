package com.example.coordination;

import com.example.coordination.api.dto.BrandMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoriesMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoryMinMaxPriceResponseDto;
import com.example.coordination.api.service.CoordinationService;
import com.example.coordination.domain.enums.CategoryType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class CoordinationServiceTest {
    @Autowired
    CoordinationService coordinationService;

    @Test
    void getCategoriesMinPriceTest() {
        GetCategoriesMinPriceResponseDto categoriesMinPrice = coordinationService.getCategoriesMinPrice();
        log.info(categoriesMinPrice.toString());

        Assertions.assertEquals(34_100, categoriesMinPrice.totalPrice());
        Assertions.assertEquals(8, categoriesMinPrice.categoryMinPriceDtos().size());
    }

    @Test
    void getBrandMinPriceTest() {
        BrandMinPriceResponseDto brandMinPrice = coordinationService.getBrandMinPrice();
        log.info(brandMinPrice.toString());

        Assertions.assertEquals("D", brandMinPrice.brandName());
        Assertions.assertEquals(36_100, brandMinPrice.totalPrice());
        Assertions.assertEquals(8, brandMinPrice.categoryPriceDtos().size());
    }

    @Test
    void getCategoryMinMaxPriceTest() {
        final String category = CategoryType.TOPS.name();
        GetCategoryMinMaxPriceResponseDto categoryMinMaxPrice = coordinationService.getCategoryMinMaxPrice(category);
        log.info(categoryMinMaxPrice.toString());

        Assertions.assertEquals("상의", categoryMinMaxPrice.categoryType().getValue());
        Assertions.assertEquals(10_000, categoryMinMaxPrice.minPrice().price());
        Assertions.assertEquals("C", categoryMinMaxPrice.minPrice().brandName());
        Assertions.assertEquals("I", categoryMinMaxPrice.maxPrice().brandName());
        Assertions.assertEquals(11_400, categoryMinMaxPrice.maxPrice().price());
    }
}
