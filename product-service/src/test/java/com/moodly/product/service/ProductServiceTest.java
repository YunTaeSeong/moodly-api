package com.moodly.product.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.product.domain.Product;
import com.moodly.product.dto.ProductDto;
import com.moodly.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("상품_전체_조회")
    public void 상품_전체_조회() {
        // given
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        // when
        List<ProductDto> allProducts = productService.getAllProducts();

        // then
        assertNotNull(allProducts);
        assertEquals(allProducts.size(), 2);
    }

    @DisplayName("상품이 없으면 빈 리스트 반환")
    @Test
    void 상품이_없으면_빈_리스트_반환() {
        // given
        when(productRepository.findAll()).thenReturn(List.of());

        // when
        List<ProductDto> allProducts = productService.getAllProducts();

        // then
        assertTrue(allProducts.isEmpty());
        assertEquals(0, allProducts.size());
    }

    @DisplayName("상품 단건 조회")
    @Test
    void 상품_단건_조회() {
        // given
        Long productId = 1L;
        Product product = mock(Product.class);

        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));

        // when
        ProductDto productById = productService.getProductById(productId);

        // then
        assertNotNull(productById);
        assertEquals(product.getId(), productById.getId());
    }

    @DisplayName("상품 단건 조회 productId 존재 하지 않으면 예외 처리")
    @Test
    void 상품_product_Id_존재하지_않으면_예외_처리() {
        // given
        Long productId = 12345L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> productService.getProductById(productId)
        );

        // then
        assertEquals(GlobalErrorCode.PRODUCTID_NOT_FOUND, baseException.getErrorCode());
    }


}