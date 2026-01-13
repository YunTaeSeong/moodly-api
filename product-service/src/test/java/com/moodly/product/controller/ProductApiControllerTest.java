package com.moodly.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodly.product.domain.Product;
import com.moodly.product.repository.ProductRepository;
import com.moodly.product.response.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ProductApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("상품 전체 조회 성공")
    void getAllProducts_success() throws Exception {
        // given
        productRepository.save(
                Product.builder()
                        .name("상품1")
                        .price(BigDecimal.valueOf(1000))
                        .build()
        );

        // when & then
        mockMvc.perform(get("/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 단건 조회 성공")
    void getProductById_success() throws Exception {
        // given
        Product saved = productRepository.save(
                Product.builder()
                        .name("상품1")
                        .price(BigDecimal.valueOf(1000))
                        .build()
        );

        // when & then
        mockMvc.perform(get("/product/{productId}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("상품1"))
                .andExpect(jsonPath("$.price").value(1000));
    }

    @Test
    @DisplayName("존재하지 않는 상품 조회 시 에러 반환")
    void getProductById_notFound() throws Exception {
        // when & then
        mockMvc.perform(get("/product/{productId}", 999L))
                .andExpect(status().isNotFound());
    }

}