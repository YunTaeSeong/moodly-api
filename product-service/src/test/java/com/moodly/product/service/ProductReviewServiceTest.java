package com.moodly.product.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.product.client.OrderItemReviewEligibilityDto;
import com.moodly.product.client.OrderServiceClient;
import com.moodly.product.domain.ProductReview;
import com.moodly.product.dto.ProductReviewDto;
import com.moodly.product.repository.ProductRepository;
import com.moodly.product.repository.ProductReviewRepository;
import com.moodly.product.request.ProductReviewCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductReviewServiceTest {

    @InjectMocks
    private ProductReviewService productReviewService;

    @Mock
    private ProductReviewRepository productReviewRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderServiceClient orderServiceClient;

    // --------------------------------------------
    // USER
    // --------------------------------------------

    @Test
    @DisplayName("구매후기 작성 : 성공")
    void 구매후기_작성_성공() {
        // given
        Long userId = 1L;
        Long productId = 2L;
        Long orderItemId = 10L;
        ProductReviewCreateRequest request = new ProductReviewCreateRequest(
                orderItemId, productId, 5, "좋아요", List.of("https://img/1.png")
        );

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productReviewRepository.existsByOrderItemId(orderItemId)).thenReturn(false);

        OrderItemReviewEligibilityDto eligibility = new OrderItemReviewEligibilityDto();
        eligibility.setProductId(productId);
        eligibility.setProductName("테스트상품");
        eligibility.setProductImage("https://img/product.png");
        eligibility.setEligible(true);
        when(orderServiceClient.getReviewEligibility(orderItemId, userId)).thenReturn(eligibility);

        ProductReview saved = ProductReview.builder()
                .id(100L)
                .orderItemId(orderItemId)
                .productId(productId)
                .userId(userId)
                .productName("테스트상품")
                .productImage("https://img/product.png")
                .rating(5)
                .content("좋아요")
                .build();
        when(productReviewRepository.save(any(ProductReview.class))).thenReturn(saved);

        // when
        ProductReviewDto result = productReviewService.createReview(userId, request);

        // then
        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals(orderItemId, result.getOrderItemId());
        assertEquals(productId, result.getProductId());

        ArgumentCaptor<ProductReview> captor = ArgumentCaptor.forClass(ProductReview.class);
        verify(productReviewRepository).save(captor.capture());
        assertEquals(userId, captor.getValue().getUserId());
        assertEquals("좋아요", captor.getValue().getContent());
        verify(orderServiceClient).getReviewEligibility(orderItemId, userId);
    }

    @Test
    @DisplayName("구매후기 작성 : 실패(상품 찾을 수 없음)")
    void 구매후기_작성_실패_상품_찾을_수_없음() {
        // given
        Long userId = 1L;
        ProductReviewCreateRequest request = new ProductReviewCreateRequest(
                10L, 2L, 5, "좋아요", null
        );

        when(productRepository.existsById(2L)).thenReturn(false);

        // when & then
        assertThrows(BaseException.class,
                () -> productReviewService.createReview(userId, request));

        verify(productRepository).existsById(2L);
        verify(productReviewRepository, never()).save(any());
        verify(orderServiceClient, never()).getReviewEligibility(any(), any());
    }

    @Test
    @DisplayName("상품별 구매후기 조회 : 성공")
    void 상품별_구매후기_조회_성공() {
        // given
        Long productId = 2L;
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        ProductReview review = mock(ProductReview.class);
        Page<ProductReview> page = new PageImpl<>(List.of(review), pageable, 1);

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productReviewRepository.findByProductIdOrderByCreatedAtDesc(productId, pageable))
                .thenReturn(page);

        // when
        Page<ProductReviewDto> result = productReviewService.getReviewsByProduct(productId, pageable);

        // then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(productReviewRepository).findByProductIdOrderByCreatedAtDesc(productId, pageable);
    }

    @Test
    @DisplayName("상품별 구매후기 조회 : 실패(상품 찾을 수 없음)")
    void 상품별_구매후기_조회_실패_상품_찾을_수_없음() {
        // given
        Long productId = 2L;
        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.existsById(productId)).thenReturn(false);

        // when & then
        assertThrows(BaseException.class,
                () -> productReviewService.getReviewsByProduct(productId, pageable));

        verify(productReviewRepository, never())
                .findByProductIdOrderByCreatedAtDesc(any(), any());
    }

    @Test
    @DisplayName("내 구매후기 조회 : 성공")
    void 내_구매후기_조회_성공() {
        // given
        Long userId = 1L;

        ProductReview review = ProductReview.builder()
                .id(1L)
                .orderItemId(10L)
                .productId(2L)
                .userId(userId)
                .rating(4)
                .content("만족")
                .build();
        when(productReviewRepository.findByUserIdOrderByCreatedAtDesc(userId))
                .thenReturn(List.of(review));

        // when
        List<ProductReviewDto> result = productReviewService.getMyReviews(userId);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getUserId());
        verify(productReviewRepository).findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Test
    @DisplayName("내 구매후기 조회 : 실패(userId null)")
    void 내_구매후기_조회_실패_userId_null() {
        // when & then
        assertThrows(BaseException.class,
                () -> productReviewService.getMyReviews(null));

        verify(productReviewRepository, never()).findByUserIdOrderByCreatedAtDesc(any());
    }

    @Test
    @DisplayName("주문 항목 후기 존재 여부 : 성공(존재)")
    void 주문_항목_후기_존재_여부_성공() {
        // given
        Long orderItemId = 10L;
        when(productReviewRepository.existsByOrderItemId(orderItemId)).thenReturn(true);

        // when
        boolean result = productReviewService.existsByOrderItem(orderItemId);

        // then
        assertTrue(result);
        verify(productReviewRepository).existsByOrderItemId(orderItemId);
    }

    @Test
    @DisplayName("주문 항목 후기 존재 여부 : 실패(미존재)")
    void 주문_항목_후기_존재_여부_실패() {
        // given
        Long orderItemId = 10L;
        when(productReviewRepository.existsByOrderItemId(orderItemId)).thenReturn(false);

        // when
        boolean result = productReviewService.existsByOrderItem(orderItemId);

        // then
        assertFalse(result);
        verify(productReviewRepository).existsByOrderItemId(orderItemId);
    }

    // --------------------------------------------
    // ADMIN
    // --------------------------------------------

    private AuthPrincipal adminPrincipal() {
        AuthPrincipal principal = mock(AuthPrincipal.class);
        when(principal.isAdmin()).thenReturn(true);
        return principal;
    }

    private AuthPrincipal userPrincipal() {
        AuthPrincipal principal = mock(AuthPrincipal.class);
        when(principal.isAdmin()).thenReturn(false);
        return principal;
    }

    @Test
    @DisplayName("관리자 전체 구매후기 조회 : 성공")
    void 관리자_전체_구매후기_조회_성공() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        ProductReview review = mock(ProductReview.class);
        Page<ProductReview> page = new PageImpl<>(List.of(review), pageable, 1);

        when(productReviewRepository.findAllByOrderByCreatedAtDesc(pageable)).thenReturn(page);

        // when
        Page<ProductReviewDto> result = productReviewService.getAllAdminReviews(pageable);

        // then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(productReviewRepository).findAllByOrderByCreatedAtDesc(pageable);
    }

    @Test
    @DisplayName("관리자 전체 구매후기 조회 : 실패(목록 없음)")
    void 관리자_전체_구매후기_조회_실패() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductReview> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(productReviewRepository.findAllByOrderByCreatedAtDesc(pageable)).thenReturn(emptyPage);

        // when
        Page<ProductReviewDto> result = productReviewService.getAllAdminReviews(pageable);

        // then
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        verify(productReviewRepository).findAllByOrderByCreatedAtDesc(pageable);
    }

    @Test
    @DisplayName("관리자 구매후기 답변 : 성공")
    void 관리자_구매후기_답변_성공() {
        // given
        AuthPrincipal admin = adminPrincipal();
        Long reviewId = 1L;
        String reply = "답변드립니다";

        ProductReview review = spy(ProductReview.builder()
                .id(reviewId)
                .orderItemId(10L)
                .productId(2L)
                .userId(5L)
                .rating(5)
                .content("후기")
                .reply(null)
                .build());

        when(productReviewRepository.findWithImagesById(reviewId)).thenReturn(Optional.of(review));

        // when
        ProductReviewDto result = productReviewService.adminReply(admin, reviewId, reply);

        // then
        assertNotNull(result);
        verify(review).setAdminReply("ADMIN", reply);
        verify(productReviewRepository).findWithImagesById(reviewId);
    }

    @Test
    @DisplayName("관리자 구매후기 답변 : 실패(이미 답변됨)")
    void 관리자_구매후기_답변_실패_이미_답변됨() {
        // given
        AuthPrincipal admin = adminPrincipal();
        Long reviewId = 1L;

        ProductReview review = mock(ProductReview.class);
        when(review.getReply()).thenReturn("기존 답변");
        when(productReviewRepository.findWithImagesById(reviewId)).thenReturn(Optional.of(review));

        // when & then
        assertThrows(BaseException.class,
                () -> productReviewService.adminReply(admin, reviewId, "새 답변"));

        verify(review, never()).setAdminReply(any(), any());
    }

    @Test
    @DisplayName("관리자 구매후기 삭제 : 성공")
    void 관리자_구매후기_삭제_성공() {
        // given
        AuthPrincipal admin = adminPrincipal();
        Long reviewId = 1L;

        ProductReview review = mock(ProductReview.class);
        when(productReviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        // when
        assertDoesNotThrow(() -> productReviewService.adminDelete(admin, reviewId));

        // then
        verify(productReviewRepository).findById(reviewId);
        verify(productReviewRepository).delete(review);
    }

    @Test
    @DisplayName("관리자 구매후기 삭제 : 실패(관리자 아님)")
    void 관리자_구매후기_삭제_실패_관리자_아님() {
        // given
        AuthPrincipal user = userPrincipal();

        // when & then
        assertThrows(BaseException.class,
                () -> productReviewService.adminDelete(user, 1L));

        verify(productReviewRepository, never()).findById(any());
        verify(productReviewRepository, never()).delete(any());
    }
}
