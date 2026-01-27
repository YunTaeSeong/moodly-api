package com.moodly.user.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.user.domain.DeliveryAddress;
import com.moodly.user.dto.DeliveryAddressDto;
import com.moodly.user.repository.DeliveryAddressRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryAddressServiceTest {

    @InjectMocks
    private DeliveryAddressService deliveryAddressService;

    @Mock
    private DeliveryAddressRepository deliveryAddressRepository;

    @DisplayName("배송지 생성 : 성공")
    @Test
    void 배송지_생성_성공() {
        // given
        Long userId = 1L;
        String postcode = "12345";
        String address = "서울특별시 광진구";
        String detailAddress = "3001";
        String recipient = "수령인";
        String phoneNumber = "010-1111-2222";
        Boolean isDefault = true;

        DeliveryAddress deliveryAddress = mock(DeliveryAddress.class);
        when(deliveryAddressRepository.save(any(DeliveryAddress.class))).thenReturn(deliveryAddress);

        // when
        DeliveryAddressDto result = deliveryAddressService.create(userId, postcode, address, detailAddress, recipient, phoneNumber, isDefault);

        // then
        ArgumentCaptor<DeliveryAddress> captor = ArgumentCaptor.forClass(DeliveryAddress.class);
        verify(deliveryAddressRepository).save(captor.capture());

        DeliveryAddress value = captor.getValue();

        assertEquals(userId, value.getUserId());
        assertEquals(postcode, value.getPostcode());
        assertEquals(address, value.getAddress());
        assertEquals(detailAddress, value.getDetailAddress());
        assertEquals(recipient, value.getRecipient());
        assertEquals(phoneNumber, value.getPhoneNumber());
        assertEquals(isDefault, value.getIsDefault());

        assertNotNull(result);
    }

    @DisplayName("배송지 생성 : 실패")
    @Test
    void 배송지_생성_실패() {
        // given
        when(deliveryAddressRepository.save(any())).thenReturn(mock(DeliveryAddress.class));

        deliveryAddressService.create(
                1L,
                "123456",
                "서울특별시 광진구",
                "3001",
                "수령인",
                "010-1111-2222",
                false
        );

        // when & then
        verify(deliveryAddressRepository, never()).clearDefaultByUserId(anyLong());
        verify(deliveryAddressRepository).save(any());
    }

    @DisplayName("배송지 전체 조회")
    @Test
    void 배송지_전체_조회() {
        // given
        Long userId = 1L;

        DeliveryAddress e1 = mock(DeliveryAddress.class);
        DeliveryAddress e2 = mock(DeliveryAddress.class);

        // when
        when(deliveryAddressRepository.findAllByUserId(userId))
                .thenReturn(List.of(e1, e2));

        List<DeliveryAddressDto> result = deliveryAddressService.findAllByUserId(userId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(deliveryAddressRepository, times(1)).findAllByUserId(userId);

    }

    @DisplayName("배송지 전체 조회 빈 리스트")
    @Test
    void 배송지_전체_조회_빈_리스트() {
        // given
        Long userId = 1L;
        when(deliveryAddressRepository.findAllByUserId(userId))
                .thenReturn(List.of());

        // when
        List<DeliveryAddressDto> result = deliveryAddressService.findAllByUserId(userId);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(deliveryAddressRepository, times(1)).findAllByUserId(userId);
    }

    @DisplayName("배송지 단건 조회")
    @Test
    void 배송지_단건_조회() {
        // given
        Long id = 1L;
        Long userId = 10L;
        when(deliveryAddressRepository.findByIdAndUserId(id, userId))
                .thenReturn(Optional.of(mock(DeliveryAddress.class)));

        // when
        DeliveryAddressDto result = deliveryAddressService.findByIdAndUserId(id, userId);

        // then
        assertNotNull(result);

        verify(deliveryAddressRepository, times(1)).findByIdAndUserId(id, userId);
    }

    @DisplayName("배송지 단건 조회 실패")
    @Test
    void 배송지_단건_조회_실패() {
        // given
        Long id = 1L;
        Long userId = 10L;

        when(deliveryAddressRepository.findByIdAndUserId(id, userId))
                .thenReturn(Optional.empty());

        // when
        BaseException ex = assertThrows(
                BaseException.class,
                () -> deliveryAddressService.findByIdAndUserId(id, userId)
        );

        // then
        assertEquals(GlobalErrorCode.INTERNAL_SERVER_ERROR, ex.getErrorCode());

        verify(deliveryAddressRepository, times(1)).findByIdAndUserId(id, userId);
    }

    @DisplayName("배송지 수정")
    @Test
    void 배송지_수정() {
        // given
        Long id = 1L;
        Long userId = 10L;
        String postcode = "12345678";
        String address = "서울특별시 광진구 업데이트";
        String detailAddress = "3002";
        String recipient = "수령인 업데이트";
        String phoneNumber = "010-1111-3333";

        DeliveryAddress deliveryAddress = mock(DeliveryAddress.class);

        when(deliveryAddressRepository.findByIdAndUserId(id, userId))
                .thenReturn(Optional.of(deliveryAddress));

        when(deliveryAddressRepository.save(any())).thenReturn(deliveryAddress);

        // when
        DeliveryAddressDto result = deliveryAddressService.update(id, userId, postcode, address, detailAddress, recipient, phoneNumber);

        // then
        assertNotNull(result);

        verify(deliveryAddressRepository, times(1)).findByIdAndUserId(id, userId);
        verify(deliveryAddress).update(postcode, address, detailAddress, recipient, phoneNumber);
        verify(deliveryAddressRepository, times(1)).save(deliveryAddress);
    }

    @DisplayName("배송지 수정 실패")
    @Test
    void 배송지_수정_실패() {
        // given
        Long id = 1L;
        Long userId = 10L;

        when(deliveryAddressRepository.findByIdAndUserId(id, userId))
                .thenReturn(Optional.empty());

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> deliveryAddressService.update(
                        id, userId, "12345678", "서울 특별시광진구 업데이트", "3002", "수령인 업데이트", "010-1111-3333"
                )
        );

        // then
        assertEquals(GlobalErrorCode.INTERNAL_SERVER_ERROR, baseException.getErrorCode());

        verify(deliveryAddressRepository, times(1)).findByIdAndUserId(id, userId);
        verify(deliveryAddressRepository, never()).save(any(DeliveryAddress.class));
    }

    @DisplayName("배송지 삭제")
    @Test
    void 배송지_삭제() {
        // given
        Long id = 1L;
        Long userId = 10L;

        when(deliveryAddressRepository.existsByIdAndUserId(id, userId))
                .thenReturn(Boolean.TRUE);

        // when
        deliveryAddressService.delete(id, userId);

        // then
        verify(deliveryAddressRepository, times(1)).existsByIdAndUserId(id, userId);
        verify(deliveryAddressRepository).deleteById(id);
    }

    @DisplayName("배송지 삭제 실패")
    @Test
    void 배송지_삭제_실패() {
        // given
        Long id = 1L;
        Long userId = 10L;
        when(deliveryAddressRepository.existsByIdAndUserId(id, userId))
                .thenReturn(Boolean.FALSE);

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> deliveryAddressService.delete(id, userId)
        );

        // then
        assertEquals(GlobalErrorCode.INTERNAL_SERVER_ERROR, baseException.getErrorCode());
        verify(deliveryAddressRepository, times(1)).existsByIdAndUserId(id, userId);
        verify(deliveryAddressRepository, never()).deleteById(id);
    }

    @DisplayName("기존 배송지 설정")
    @Test
    void 기존_배송지_설정() {
        // given
        Long id = 1L;
        Long userId = 10L;

        DeliveryAddress deliveryAddress = mock(DeliveryAddress.class);

        when(deliveryAddressRepository.findByIdAndUserId(id, userId))
                .thenReturn(Optional.of(deliveryAddress));

        when(deliveryAddressRepository.save(any())).thenReturn(deliveryAddress);

        // when
        DeliveryAddressDto result = deliveryAddressService.deliveryAddressDefault(id, userId);

        // then
        assertNotNull(result);

        InOrder inOrder = inOrder(deliveryAddressRepository, deliveryAddress);
        inOrder.verify(deliveryAddressRepository).findByIdAndUserId(id, userId);
        inOrder.verify(deliveryAddressRepository).clearDefaultByUserId(userId);
        inOrder.verify(deliveryAddress).updateDefault(true);
        inOrder.verify(deliveryAddressRepository).save(deliveryAddress);
    }

    @DisplayName("기존 배송지 설정 실패")
    @Test
    void 기존_배송지_설정_실패() {
        // given
        Long id = 1L;
        Long userId = 10L;

        when(deliveryAddressRepository.findByIdAndUserId(id, userId))
                .thenReturn(Optional.empty());

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> deliveryAddressService.deliveryAddressDefault(id, userId)
        );

        // then
        assertEquals(GlobalErrorCode.INTERNAL_SERVER_ERROR, baseException.getErrorCode());
        verify(deliveryAddressRepository, never()).clearDefaultByUserId(userId);
        verify(deliveryAddressRepository, never()).save(any(DeliveryAddress.class));
    }

}