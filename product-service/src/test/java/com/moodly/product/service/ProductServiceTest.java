package com.moodly.product.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.product.domain.Category;
import com.moodly.product.domain.Product;
import com.moodly.product.domain.SubCategory;
import com.moodly.product.dto.ProductDto;
import com.moodly.product.repository.CategoryRepository;
import com.moodly.product.repository.ProductRepository;
import com.moodly.product.repository.SubCategoryRepository;
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

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SubCategoryRepository subCategoryRepository;

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

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // when
        when(product.getId()).thenReturn(productId);
        when(product.getCategoryId()).thenReturn(null);
        when(product.getSubCategoryId()).thenReturn(null);

        ProductDto result = productService.getProductById(productId);

        // then
        assertNotNull(result);
        assertEquals(productId, result.getId());
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

    @Test
    @DisplayName("상품 전체 조회 : 카테고리, 서브카테고리 매핑")
    void 상품_전체_조회_카테고리_서브카테고리() {
        // given
        Product p1 = mock(Product.class);
        Product p2 = mock(Product.class);
        Product p3 = mock(Product.class);

        // 상품 3개 (카테고리 + 서비카테고리), (서브 카테고리), (둘 다 Null)
        when(p1.getId()).thenReturn(1L);
        when(p1.getCategoryId()).thenReturn(10L);
        when(p1.getSubCategoryId()).thenReturn(100L);

        when(p2.getId()).thenReturn(2L);
        when(p2.getCategoryId()).thenReturn(20L);
        when(p2.getSubCategoryId()).thenReturn(null);

        when(p3.getId()).thenReturn(3L);
        when(p3.getCategoryId()).thenReturn(null);
        when(p3.getSubCategoryId()).thenReturn(null);

        when(productRepository.findAll()).thenReturn(List.of(p1, p2, p3));

        Category c1 = mock(Category.class);
        Category c2 = mock(Category.class);
        when(c1.getId()).thenReturn(10L);
        when(c1.getName()).thenReturn("의류");

        when(c2.getId()).thenReturn(20L);
        when(c2.getName()).thenReturn("패션");

        when(categoryRepository.findAll()).thenReturn(List.of(c1, c2));

        SubCategory s1 = mock(SubCategory.class);
        when(s1.getId()).thenReturn(100L);
        when(s1.getName()).thenReturn("의류 서브카테고리");

        when(subCategoryRepository.findAll()).thenReturn(List.of(s1));

        // when
        List<ProductDto> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(3, result.size());

        ProductDto dto1 = result.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals("의류", dto1.getCategoryName());
        assertEquals("의류 서브카테고리", dto1.getSubCategoryName());

        ProductDto dto2 = result.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals("패션", dto2.getCategoryName());
        assertNull(dto2.getSubCategoryName());

        ProductDto dto3 = result.get(2);
        assertEquals(3L, dto3.getId());
        assertNull(dto3.getCategoryName());
        assertNull(dto3.getSubCategoryName());

        // then
        verify(productRepository).findAll();
        verify(categoryRepository).findAll();
        verify(subCategoryRepository).findAll();
    }

    @DisplayName("상품 상세 정보 : 카테고리,서브카테고리 이름 매핑")
    @Test
    void 상품_상세_정보() {
        // given
        Long productId = 1L;
        Product product = mock(Product.class);

        when(product.getId()).thenReturn(productId);
        when(product.getCategoryId()).thenReturn(10L);
        when(product.getSubCategoryId()).thenReturn(100L);

        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));

        Category category = mock(Category.class);
        when(category.getName()).thenReturn("의류");
        when(categoryRepository.findById(10L)).thenReturn(Optional.ofNullable(category));

        SubCategory subCategory = mock(SubCategory.class);
        when(subCategory.getName()).thenReturn("의류 서브카테고리");
        when(subCategoryRepository.findById(100L)).thenReturn(Optional.ofNullable(subCategory));

        // when
        ProductDto result = productService.getProductById(productId);

        // then
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("의류", result.getCategoryName());
        assertEquals("의류 서브카테고리", result.getSubCategoryName());

        verify(productRepository).findById(productId);
        verify(categoryRepository).findById(10L);
        verify(subCategoryRepository).findById(100L);
    }

    @DisplayName("상품 상세 정보 : 카테고리 없음")
    @Test
    void 상품_상세_정보_카테고리_없음() {
        // given
        Long productId = 1L;

        Product product = mock(Product.class);
        when(product.getId()).thenReturn(productId);
        when(product.getCategoryId()).thenReturn(null);
        when(product.getSubCategoryId()).thenReturn(null);

        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));

        // when
        ProductDto result = productService.getProductById(productId);

        // then
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertNull(result.getCategoryName());
        assertNull(result.getSubCategoryName());

        verify(productRepository).findById(productId);
    }

    @DisplayName("상품 상세 정보 : 상품 없으면 예외")
    @Test
    void 상품_상세_정보_예외() {
        // given
        Long productId = 100000000L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> productService.getProductById(productId)
        );

        // then
        assertEquals(GlobalErrorCode.PRODUCTID_NOT_FOUND, baseException.getErrorCode());
        verify(productRepository).findById(productId);
    }

    @DisplayName("상품 검색")
    @Test
    void 상품_검색() {
        // given
        String keyword = "  keyword  ";
        String trim = "keyword";

        Product p1 = mock(Product.class);
        when(p1.getId()).thenReturn(1L);
        when(p1.getCategoryId()).thenReturn(10L);
        when(p1.getSubCategoryId()).thenReturn(100L);

        Product p2 = mock(Product.class);
        when(p2.getId()).thenReturn(2L);
        when(p2.getCategoryId()).thenReturn(20L);
        when(p2.getSubCategoryId()).thenReturn(null);

        when(productRepository.searchByName(trim)).thenReturn(List.of(p1, p2));

        Category c1 = mock(Category.class);
        when(c1.getId()).thenReturn(10L);
        when(c1.getName()).thenReturn("의류");

        Category c2 = mock(Category.class);
        when(c2.getId()).thenReturn(20L);
        when(c2.getName()).thenReturn("패션");

        when(categoryRepository.findAll()).thenReturn(List.of(c1, c2));

        SubCategory s1 = mock(SubCategory.class);
        when(s1.getId()).thenReturn(100L);
        when(s1.getName()).thenReturn("의류 서브카테고리");

        when(subCategoryRepository.findAll()).thenReturn(List.of(s1));

        // when
        List<ProductDto> result = productService.searchByName(keyword);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());

        ProductDto dto1 = result.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals("의류", dto1.getCategoryName());
        assertEquals("의류 서브카테고리", dto1.getSubCategoryName());

        ProductDto dto2 = result.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals("패션", dto2.getCategoryName());
        assertNull(dto2.getSubCategoryName());

        verify(productRepository).searchByName(trim);
        verify(categoryRepository).findAll();
        verify(subCategoryRepository).findAll();
    }

    @DisplayName("상품 검색 : Null 또는 Empty 면 빈 리스트 반환")
    @Test
    void 상품_검색_빈_리스트_반환() {
        // when
        List<Product> products1 = productRepository.searchByName(null);
        List<Product> products2 = productRepository.searchByName("    ");

        // then
        assertNotNull(products1);
        assertNotNull(products2);

        assertTrue(products1.isEmpty());
        assertTrue(products2.isEmpty());
    }


}