package com.example.coordination;

import com.example.coordination.api.dto.AddCategoryRequestDto;
import com.example.coordination.api.dto.BrandRequestDto;
import com.example.coordination.domain.entity.Goods;
import com.example.coordination.domain.enums.Category;
import com.example.coordination.domain.repository.GoodsRepository;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(value = "admin", password = "1234", roles = {"ADMIN"})
@AutoConfigureMockMvc
@SpringBootTest
public class AdminControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    GoodsRepository goodsRepository;
    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("상품 목록 조회 테스트")
    void getGoodsTest() throws Exception {

        ResultActions perform = mvc.perform(get("/admin/goods")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        perform.andExpect(status().isOk())
                .andReturn();
    }


    @Test
    @DisplayName("브랜드 추가 테스트")
    @Transactional
    void addBrandTest() throws Exception {
        final String testBrandName = "AA";
        BrandRequestDto request = new BrandRequestDto(testBrandName);

        ResultActions perform = mvc.perform(post("/admin/brand")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        perform.andExpect(status().isOk())
                .andReturn();

        List<Goods> goods = goodsRepository.findAllByBrandName(testBrandName);
        Assertions.assertFalse(goods.isEmpty());
    }

    @Test
    @DisplayName("브랜드 삭제 테스트")
    @Transactional
    void deleteBraneNameTest() throws Exception {
        final String testBrandName = "A";


        ResultActions perform = mvc.perform(delete("/admin/brand/{brandName}", testBrandName)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
        );

        perform.andExpect(status().isOk())
                .andReturn();

        List<Goods> goods = goodsRepository.findAllByBrandName(testBrandName);
        Assertions.assertTrue(goods.isEmpty());
    }

    @Test
    @DisplayName("카테고리 가격 수정 테스트")
    @Transactional
    void modifyCategoryTest() throws Exception {
        final String testBrandName = "A";
        final Category testCategory = Category.TOPS;
        final Integer testPrice = 300_000_000;
        AddCategoryRequestDto request = new AddCategoryRequestDto(testBrandName, testCategory, testPrice);

        ResultActions perform = mvc.perform(put("/admin/category")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        perform.andExpect(status().isOk())
                .andReturn();

        Goods goods = goodsRepository.findByBrandNameAndCategory(testBrandName, Category.TOPS).orElseThrow();
        Assertions.assertEquals(testPrice, goods.getPrice());
    }
}
