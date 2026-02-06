package com.moodly.product.service;

import com.moodly.common.exception.BaseException;
import com.moodly.product.domain.ProductInquiry;
import com.moodly.product.dto.ProductInquiryDto;
import com.moodly.product.repository.ProductInquiryRepository;
import com.moodly.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductInquiryServiceTest {

    @InjectMocks
    private ProductInquiryService productInquiryService;

    @Mock
    private ProductInquiryRepository productInquiryRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 문의 등록 : 성공")
    void 상품_문의_등록_성공() {
        // given
        Long userId = 1L;
        Long productId = 2L;
        String content = "문의합니다";

        when(productRepository.existsById(productId)).thenReturn(true);

        ProductInquiry saved = mock(ProductInquiry.class);
        when(saved.getId()).thenReturn(1L);
        when(saved.getUserId()).thenReturn(userId);
        when(saved.getProductId()).thenReturn(productId);
        when(saved.getContent()).thenReturn(content);

        when(productInquiryRepository.save(any(ProductInquiry.class))).thenReturn(saved);

        // when
        ProductInquiryDto result = productInquiryService.createProductInquiry(userId, productId, content);

        // then
        assertNotNull(result);
        verify(productRepository, times(1)).existsById(productId);
        verify(productInquiryRepository, times(1)).save(any(ProductInquiry.class));
    }

    @Test
    @DisplayName("상품 문의 등록 : 실패(상품 찾을 수 없음)")
    void 상품_문의_등록_실패_상품_찾을_수_없음() {
        // given
        Long userId = 1L;
        Long productId = 2L;

        when(productRepository.existsById(productId)).thenReturn(false);

        // when & then
        assertThrows(BaseException.class,
                () -> productInquiryService.createProductInquiry(userId, productId, "문의"));

        verify(productRepository, times(1)).existsById(productId);
        verify(productInquiryRepository, never()).save(any());
    }


    @Test
    @DisplayName("상품 문의 단건 조회 : 성공")
    void 상품_문의_단건_조회_성공() {
        // given
        Long userId = 1L;
        Long inquiryId = 2L;

        ProductInquiry inquiry = mock(ProductInquiry.class);
        when(inquiry.getUserId()).thenReturn(userId);

        when(productInquiryRepository.findById(inquiryId)).thenReturn(Optional.of(inquiry));

        // when
        ProductInquiryDto result = productInquiryService.getProductInquiry(userId, inquiryId);

        // then
        assertNotNull(result);
        verify(productInquiryRepository, times(1)).findById(inquiryId);
    }

    @Test
    @DisplayName("상품 문의 단건 조회 : 실패(다른 사람인 경우)")
    void 상품_문의_단건_조회_실패_다른_사람인_경우() {
        // given
        Long userId = 1L;
        Long inquiryId = 2L;

        ProductInquiry inquiry = mock(ProductInquiry.class);
        when(inquiry.getUserId()).thenReturn(10L); // 다른 작성자

        when(productInquiryRepository.findById(inquiryId)).thenReturn(Optional.of(inquiry));

        // when & then
        assertThrows(BaseException.class,
                () -> productInquiryService.getProductInquiry(userId, inquiryId));

        verify(productInquiryRepository, times(1)).findById(inquiryId);
    }

    @Test
    @DisplayName("상품 문의 전체 조회 : 성공")
    void 상품_문의_전체_조회_성공() {
        // given
        Long userId = 12L;
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdDate"));

        ProductInquiry inquiry = mock(ProductInquiry.class);
        Page<ProductInquiry> page = new PageImpl<>(List.of(inquiry), pageable, 1);

        when(productInquiryRepository.searchMyInquiries(eq(userId), isNull(), isNull(), isNull(), eq(pageable)))
                .thenReturn(page);

        // when
        Page<ProductInquiryDto> result = productInquiryService.getAllProductInquiry(
                userId, null, null, null, pageable
        );

        // then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(productInquiryRepository, times(1))
                .searchMyInquiries(eq(userId), isNull(), isNull(), isNull(), eq(pageable));
    }

    @Test
    @DisplayName("상품 문의 전체 조회 : 실패(userId null)")
    void 상품_문의_전체_조회_실패() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when & then
        assertThrows(BaseException.class,
                () -> productInquiryService.getAllProductInquiry(null, null, null, null, pageable));

        verify(productInquiryRepository, never())
                .searchMyInquiries(any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("상품 문의 수정 : 성공")
    void 상품_문의_수정_성공() {
        // given
        Long userId = 1L;
        Long inquiryId = 2L;

        ProductInquiry inquiry = spy(ProductInquiry.builder()
                .id(inquiryId)
                .userId(userId)
                .productId(100L)
                .content("문의")
                .build());

        when(productInquiryRepository.findById(inquiryId)).thenReturn(Optional.of(inquiry));

        // when
        ProductInquiryDto result = productInquiryService.updateProductInquiry(userId, inquiryId, "문의2");

        // then
        assertNotNull(result);
        assertEquals("문의2", inquiry.getContent());
        verify(productInquiryRepository, times(1)).findById(inquiryId);
    }

    @Test
    @DisplayName("상품 문의 수정 : 실패(다른 사람)")
    void 상품_문의_수정_실패_다른_사람() {
        // given
        Long userId = 1L;
        Long inquiryId = 2L;

        ProductInquiry inquiry = mock(ProductInquiry.class);
        when(inquiry.getUserId()).thenReturn(10L);

        when(productInquiryRepository.findById(inquiryId)).thenReturn(Optional.of(inquiry));

        // when & then
        assertThrows(BaseException.class,
                () -> productInquiryService.updateProductInquiry(userId, inquiryId, "new"));

        verify(productInquiryRepository, times(1)).findById(inquiryId);
    }

    @Test
    @DisplayName("상품 문의 삭제 : 성공")
    void 상품_문의_삭제_성공() {
        // given
        Long userId = 1L;
        Long inquiryId = 2L;

        ProductInquiry inquiry = mock(ProductInquiry.class);
        when(inquiry.getUserId()).thenReturn(userId);

        when(productInquiryRepository.findById(inquiryId)).thenReturn(Optional.of(inquiry));

        // when
        assertDoesNotThrow(() -> productInquiryService.deleteProductInquiry(userId, inquiryId));

        // then
        verify(productInquiryRepository, times(1)).findById(inquiryId);
        verify(productInquiryRepository, times(1)).delete(inquiry);
    }

    @Test
    @DisplayName("상품 문의 삭제 : 실패(다른 사람)")
    void 상품_문의_삭제_실패_다른_사람() {
        // given
        Long userId = 12L;
        Long inquiryId = 10L;

        ProductInquiry inquiry = mock(ProductInquiry.class);
        when(inquiry.getUserId()).thenReturn(1L);

        when(productInquiryRepository.findById(inquiryId)).thenReturn(Optional.of(inquiry));

        // when & then
        assertThrows(BaseException.class,
                () -> productInquiryService.deleteProductInquiry(userId, inquiryId));

        verify(productInquiryRepository, times(1)).findById(inquiryId);
        verify(productInquiryRepository, never()).delete(any());
    }

}