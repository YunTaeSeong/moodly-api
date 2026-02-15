package com.moodly.product.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.product.domain.Product;
import com.moodly.product.domain.ProductInquiry;
import com.moodly.product.dto.ProductInquiryDto;
import com.moodly.product.enums.ProductInquiryStatus;
import com.moodly.product.event.InquiryEvent;
import com.moodly.product.repository.ProductInquiryRepository;
import com.moodly.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    @Mock
    private InquiryEventService inquiryEventService;

    // --------------------------------------------
    // USER
    // --------------------------------------------
    @Test
    @DisplayName("상품 문의 등록 : 성공 (+ 이벤트 발행)")
    void 상품_문의_등록_성공() {
        Long userId = 1L;
        Long productId = 2L;
        String content = "문의합니다";

        when(productRepository.existsById(productId)).thenReturn(true);

        Product product = mock(Product.class);
        when(product.getName()).thenReturn("테스트상품");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductInquiry saved = mock(ProductInquiry.class);
        when(saved.getId()).thenReturn(1L);
        when(productInquiryRepository.save(any(ProductInquiry.class))).thenReturn(saved);

        ProductInquiryDto result = productInquiryService.createProductInquiry(userId, productId, content);

        assertNotNull(result);

        ArgumentCaptor<InquiryEvent> captor = ArgumentCaptor.forClass(InquiryEvent.class);
        verify(inquiryEventService, times(1)).publishEvent(captor.capture());

        InquiryEvent event = captor.getValue();
        assertEquals(1L, event.getInquiryId());
        assertEquals(productId, event.getProductId());
        assertEquals("테스트상품", event.getProductName());
        assertEquals(userId, event.getAuthorUserId());
    }

    @Test
    @DisplayName("상품 문의 등록 : 실패(상품 찾을 수 없음) (+ 이벤트 발행 안 함)")
    void 상품_문의_등록_실패_상품_찾을_수_없음() {
        // given
        Long userId = 1L;
        Long productId = 2L;

        when(productRepository.existsById(productId)).thenReturn(false);

        // when & then
        assertThrows(BaseException.class,
                () -> productInquiryService.createProductInquiry(userId, productId, "문의"));

        // then
        verify(productRepository, times(1)).existsById(productId);

        verify(productInquiryRepository, never()).save(any());
        verify(productRepository, never()).findById(any());
        verify(inquiryEventService, never()).publishEvent(any());
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
    @DisplayName("상품 문의 수정 : 성공(PENDING, reply 없음)")
    void 상품_문의_수정_성공() {
        // given
        Long userId = 1L;
        Long inquiryId = 2L;

        // ✅ reply=null, status=PENDING 이어야 성공
        ProductInquiry inquiry = spy(ProductInquiry.builder()
                .id(inquiryId)
                .userId(userId)
                .productId(100L)
                .content("문의")
                .reply(null)
                .status(ProductInquiryStatus.PENDING)
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
    @DisplayName("상품 문의 수정 : 실패(답변 완료된 문의)")
    void 상품_문의_수정_실패_답변완료() {
        // given
        Long userId = 1L;
        Long inquiryId = 2L;

        ProductInquiry inquiry = mock(ProductInquiry.class);
        when(inquiry.getUserId()).thenReturn(userId);
        when(inquiry.getReply()).thenReturn("답변");

        when(productInquiryRepository.findById(inquiryId)).thenReturn(Optional.of(inquiry));

        // when & then
        assertThrows(BaseException.class,
                () -> productInquiryService.updateProductInquiry(userId, inquiryId, "new"));

        verify(productInquiryRepository, times(1)).findById(inquiryId);
    }

    @Test
    @DisplayName("상품 문의 삭제 : 성공(PENDING, reply 없음)")
    void 상품_문의_삭제_성공() {
        // given
        Long userId = 1L;
        Long inquiryId = 2L;

        ProductInquiry inquiry = mock(ProductInquiry.class);
        when(inquiry.getUserId()).thenReturn(userId);
        when(inquiry.getReply()).thenReturn(null);
        when(inquiry.getStatus()).thenReturn(ProductInquiryStatus.PENDING);

        when(productInquiryRepository.findById(inquiryId)).thenReturn(Optional.of(inquiry));

        // when
        assertDoesNotThrow(() -> productInquiryService.deleteProductInquiry(userId, inquiryId));

        // then
        verify(productInquiryRepository, times(1)).findById(inquiryId);
        verify(productInquiryRepository, times(1)).delete(inquiry);
    }

    @Test
    @DisplayName("상품 문의 삭제 : 실패(답변 완료된 문의)")
    void 상품_문의_삭제_실패_답변완료() {
        // given
        Long userId = 1L;
        Long inquiryId = 2L;

        ProductInquiry inquiry = mock(ProductInquiry.class);
        when(inquiry.getUserId()).thenReturn(userId);
        when(inquiry.getReply()).thenReturn("답변");

        when(productInquiryRepository.findById(inquiryId)).thenReturn(Optional.of(inquiry));

        // when & then
        assertThrows(BaseException.class,
                () -> productInquiryService.deleteProductInquiry(userId, inquiryId));

        verify(productInquiryRepository, times(1)).findById(inquiryId);
        verify(productInquiryRepository, never()).delete(any());
    }



    // --------------------------------------------
    // ADMIN
    // --------------------------------------------

    /**
     * 공통 메소드
     */
    // 관리자 권한만 필요한 경우 (조회/수정/삭제)
    private AuthPrincipal adminPrincipalOnly() {
        AuthPrincipal principal = mock(AuthPrincipal.class);
        when(principal.isAdmin()).thenReturn(true);
        return principal;
    }

    // 관리자 답변용 (userId 필요)
    private AuthPrincipal adminPrincipalWithId(Long adminId) {
        AuthPrincipal principal = mock(AuthPrincipal.class);
        when(principal.isAdmin()).thenReturn(true);
        when(principal.getUserId()).thenReturn(adminId);
        return principal;
    }

    // 관리자 아님
    private AuthPrincipal userPrincipal() {
        AuthPrincipal principal = mock(AuthPrincipal.class);
        when(principal.isAdmin()).thenReturn(false);
        return principal;
    }

    @Test
    @DisplayName("관리자 전체 문의 조회 : 성공")
    void 관리자_전체_문의_조회_성공() {
        AuthPrincipal admin = adminPrincipalOnly();
        Pageable pageable = PageRequest.of(0, 10);

        ProductInquiry inquiry = mock(ProductInquiry.class);
        Page<ProductInquiry> page = new PageImpl<>(List.of(inquiry), pageable, 1);

        when(productInquiryRepository.searchAllAdminInquiries(isNull(), isNull(), isNull(), eq(pageable))).thenReturn(page);

        Page<ProductInquiryDto> result = productInquiryService.getAllAdminProductInquiry(admin, null, null, null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        verify(productInquiryRepository).searchAllAdminInquiries(isNull(), isNull(), isNull(), eq(pageable));
    }

    @Test
    @DisplayName("관리자 전체 문의 조회 : 실패(관리자 아님)")
    void 관리자_전체_문의_조회_실패() {
        AuthPrincipal user = userPrincipal();
        Pageable pageable = PageRequest.of(0, 10);

        assertThrows(
                BaseException.class,
                () -> productInquiryService.getAllAdminProductInquiry(
                        user, null, null, null, pageable
        ));

        verify(productInquiryRepository, never()).searchAllAdminInquiries(any(), any(), any(), any());
    }

    @Test
    @DisplayName("관리자 문의 답변 : 성공 (+ Kafka 이벤트 발행)")
    void 관리자_문의_답변_성공() {
        // given
        AuthPrincipal admin = adminPrincipalWithId(99L);
        Long inquiryId = 1L;
        String reply = "답변합니다";

        ProductInquiry inquiry = mock(ProductInquiry.class);
        when(inquiry.getId()).thenReturn(inquiryId);
        when(inquiry.getProductId()).thenReturn(2L);
        when(inquiry.getUserId()).thenReturn(10L);

        when(productInquiryRepository.findById(inquiryId)).thenReturn(Optional.of(inquiry));

        Product product = mock(Product.class);
        when(product.getName()).thenReturn("테스트상품");
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));

        // when
        ProductInquiryDto result = productInquiryService.getAdminReply(admin, inquiryId, reply);

        // then
        assertNotNull(result);
        verify(inquiry).setReplyAdmin(99L, "ADMIN", reply);

        // Kafka 이벤트 발행 검증
        ArgumentCaptor<InquiryEvent> eventCaptor = ArgumentCaptor.forClass(InquiryEvent.class);
        verify(inquiryEventService, times(1)).publishEvent(eventCaptor.capture());

        InquiryEvent event = eventCaptor.getValue();
        assertEquals("INQUIRY_REPLIED", event.getType());
        assertEquals(inquiryId, event.getInquiryId());
        assertEquals(2L, event.getProductId());
        assertEquals("테스트상품", event.getProductName());
        assertEquals(10L, event.getTargetUserId()); // 답변 알림 대상 = 문의 작성자
        String expectedPreview = reply.length() > 50 ? reply.substring(0, 50) + "..." : reply;
        assertEquals(expectedPreview, event.getReplyPreview());
    }

    @Test
    @DisplayName("관리자 문의 답변 : 실패(관리자 아님)")
    void 관리자_문의_답변_실패() {
        // given
        AuthPrincipal user = userPrincipal();

        // when & then
        assertThrows(
                BaseException.class,
                () -> productInquiryService.getAdminReply(
                        user, 1L, "reply"
                )
        );

        // then
        verify(productInquiryRepository, never()).findById(any());
        verify(inquiryEventService, never()).publishEvent(any());
        verify(inquiryEventService, never()).publishEvent(any());
    }

    @Test
    @DisplayName("관리자 문의 수정 : 성공")
    void 관리자_문의_수정_성공() {
        AuthPrincipal admin = adminPrincipalOnly();
        Long inquiryId = 1L;

        ProductInquiry inquiry = mock(ProductInquiry.class);
        when(productInquiryRepository.findById(inquiryId))
                .thenReturn(Optional.of(inquiry));

        ProductInquiryDto result = productInquiryService.getAdminUpdate(admin, inquiryId, "수정 내용");

        assertNotNull(result);
        verify(inquiry).setContent("수정 내용");
    }

    @Test
    @DisplayName("관리자 문의 수정 : 실패(관리자 아님)")
    void 관리자_문의_수정_실패() {
        AuthPrincipal user = userPrincipal();

        assertThrows(
                BaseException.class,
                () -> productInquiryService.getAdminUpdate(
                        user, 1L, "content"
        ));

        verify(productInquiryRepository, never()).findById(any());
    }

    @Test
    @DisplayName("관리자 문의 삭제 : 성공")
    void 관리자_문의_삭제_성공() {
        AuthPrincipal admin = adminPrincipalOnly();
        Long inquiryId = 1L;

        ProductInquiry inquiry = mock(ProductInquiry.class);
        when(productInquiryRepository.findById(inquiryId)).thenReturn(Optional.of(inquiry));

        assertDoesNotThrow(() ->
                productInquiryService.getAdminDelete(admin, inquiryId)
        );

        verify(productInquiryRepository).delete(inquiry);
    }

    @Test
    @DisplayName("관리자 문의 삭제 : 실패(관리자 아님)")
    void 관리자_문의_삭제_실패() {
        AuthPrincipal user = userPrincipal();

        assertThrows(BaseException.class,
                () -> productInquiryService.getAdminDelete(user, 1L)
        );

        verify(productInquiryRepository, never()).delete(any());
    }
}