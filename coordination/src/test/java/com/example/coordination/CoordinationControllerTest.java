package com.example.coordination;

import com.example.coordination.api.dto.BrandMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoriesMinPriceResponseDto;
import com.example.coordination.api.dto.GetCategoryMinMaxPriceResponseDto;
import com.example.coordination.common.util.ObjectMapperFactory;
import com.example.coordination.domain.enums.CategoryType;
import com.example.coordination.domain.repository.GoodsRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(value = "user", password = "1234", roles = {"CLIENT"})
@AutoConfigureMockMvc
@SpringBootTest
public class CoordinationControllerTest {
    private static final ObjectMapper OBJECT_MAPPER = ObjectMapperFactory.getInstance();

    @Autowired
    MockMvc mvc;
    @Autowired
    GoodsRepository goodsRepository;

    @Test
    @DisplayName("카테고리 최저 가격 조회 테스트")
    void getCategoriesMinPriceTest() throws Exception {

        ResultActions perform = mvc.perform(get("/client/categories/min-price")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult mvcResult = perform.andExpect(status().isOk())
                .andReturn();

        GetCategoriesMinPriceResponseDto result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<GetCategoriesMinPriceResponseDto>() {
                });
        Assertions.assertEquals(34_100, result.totalPrice());
        Assertions.assertFalse(result.categoryMinPriceDtos().isEmpty());
    }

    @Test
    @DisplayName("최저 가격 브랜드 조회 테스트")
    void getBrandMinPriceTest() throws Exception {
        ResultActions perform = mvc.perform(get("/client/brand/min-price")
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
    }

    @Test
    @DisplayName("카테고리 최고,최저 가격 조회 테스트")
    void getCategoryMinMaxPriceTest() throws Exception {
        final String testCategory = CategoryType.TOPS.getValue();


        ResultActions perform = mvc.perform(get("/client/category/{category}/min-max-price", testCategory)
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
