package com.example.coordination;

import com.example.coordination.api.dto.*;
import com.example.coordination.common.util.ObjectMapperFactory;
import com.example.coordination.domain.enums.CategoryType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(value = "user", password = "1234", roles = {"CLIENT"})
@AutoConfigureMockMvc
@SpringBootTest
public class ApiControllerTest {
    private static final ObjectMapper OBJECT_MAPPER = ObjectMapperFactory.getInstance();

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("카테고리 최저 가격 조회 테스트")
    void getCategoriesMinPriceTest() throws Exception {

        ResultActions perform = mvc.perform(get("/api/categories/min-prices")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult mvcResult = perform.andExpect(status().isOk())
                .andReturn();

        GetCategoriesMinPriceResponseDto result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        Assertions.assertEquals(34_100, result.totalPrice());
        List<CategoryMinPriceDto> categoryMinPriceDtos = result.categoryMinPriceDtos();
        Assertions.assertFalse(categoryMinPriceDtos.isEmpty());
        for (CategoryMinPriceDto elem : categoryMinPriceDtos) {
            if (elem.category().equals(CategoryType.TOPS.getValue())) {
                Assertions.assertEquals(10_000, elem.price());
                Assertions.assertEquals("C", elem.brandName());
            }
            if (elem.category().equals(CategoryType.OUTER.getValue())) {
                Assertions.assertEquals(5_000, elem.price());
                Assertions.assertEquals("E", elem.brandName());
            }
            if (elem.category().equals(CategoryType.PANTS.getValue())) {
                Assertions.assertEquals(3_000, elem.price());
                Assertions.assertEquals("D", elem.brandName());
            }
            if (elem.category().equals(CategoryType.SNEAKERS.getValue())) {
                Assertions.assertEquals(9_000, elem.price());
                Assertions.assertEquals("G", elem.brandName());
            }
            if (elem.category().equals(CategoryType.BAG.getValue())) {
                Assertions.assertEquals(2_000, elem.price());
                Assertions.assertEquals("A", elem.brandName());
            }
            if (elem.category().equals(CategoryType.HAT.getValue())) {
                Assertions.assertEquals(1_500, elem.price());
                Assertions.assertEquals("D", elem.brandName());
            }
            if (elem.category().equals(CategoryType.SOCKS.getValue())) {
                Assertions.assertEquals(1_700, elem.price());
                Assertions.assertEquals("I", elem.brandName());
            }
            if (elem.category().equals(CategoryType.ACCESSORY.getValue())) {
                Assertions.assertEquals(1_900, elem.price());
                Assertions.assertEquals("F", elem.brandName());
            }
        }
    }

    @Test
    @DisplayName("최저 가격 브랜드 조회 테스트")
    void getBrandMinPriceTest() throws Exception {
        ResultActions perform = mvc.perform(get("/api/brands/min-price")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult mvcResult = perform.andExpect(status().isOk())
                .andReturn();

        BrandMinPriceResponseDto result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        Assertions.assertEquals("D", result.brandName());
        Assertions.assertEquals(36_100, result.totalPrice());
        Assertions.assertFalse(result.categoryPriceDtos().isEmpty());
        for (CategoryPriceDto elem : result.categoryPriceDtos()) {
            if (elem.categoryType().equals(CategoryType.TOPS)) {
                Assertions.assertEquals(10_100, elem.price());
            }
            if (elem.categoryType().equals(CategoryType.OUTER)) {
                Assertions.assertEquals(5_100, elem.price());
            }
            if (elem.categoryType().equals(CategoryType.PANTS)) {
                Assertions.assertEquals(3_000, elem.price());
            }
            if (elem.categoryType().equals(CategoryType.SNEAKERS)) {
                Assertions.assertEquals(9_500, elem.price());
            }
            if (elem.categoryType().equals(CategoryType.BAG)) {
                Assertions.assertEquals(2_500, elem.price());
            }
            if (elem.categoryType().equals(CategoryType.HAT)) {
                Assertions.assertEquals(1_500, elem.price());
            }
            if (elem.categoryType().equals(CategoryType.SOCKS)) {
                Assertions.assertEquals(2_400, elem.price());
            }
            if (elem.categoryType().equals(CategoryType.ACCESSORY)) {
                Assertions.assertEquals(2_000, elem.price());
            }
        }
    }

    @Test
    @DisplayName("카테고리 최고,최저 가격 조회 테스트")
    void getCategoryMinMaxPriceTest() throws Exception {
        final String testCategory = CategoryType.TOPS.getValue();


        ResultActions perform = mvc.perform(get("/api/categories/{category}/min-max-price", testCategory)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult mvcResult = perform.andExpect(status().isOk())
                .andReturn();

        GetCategoryMinMaxPriceResponseDto result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        Assertions.assertEquals("상의", result.categoryType().getValue());
        Assertions.assertEquals(10_000, result.minPrice().price());
        Assertions.assertEquals("C", result.minPrice().brandName());
        Assertions.assertEquals("I", result.maxPrice().brandName());
        Assertions.assertEquals(11_400, result.maxPrice().price());
    }
}