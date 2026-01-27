package com.moodly.user.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.user.domain.DeliveryAddress;
import com.moodly.user.dto.DeliveryAddressDto;
import com.moodly.user.repository.DeliveryAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;

    @Transactional
    public DeliveryAddressDto create(Long userId, String postcode, String address, String detailAddress,
                                     String recipient, String phoneNumber, Boolean isDefault) {
        // 기본 배송지로 설정하는 경우, 기존에 있던 기본 배송지 해제
        // 새로운 배송지 : isDefault : true, 기본 아닌 배송지 : isDefault : false, null
        if (Boolean.TRUE.equals(isDefault)) { // Boolean 기본 값 : null
            deliveryAddressRepository.clearDefaultByUserId(userId);
        }

        DeliveryAddress saved = deliveryAddressRepository.save(DeliveryAddress.of(userId, postcode, address, detailAddress, recipient, phoneNumber, isDefault));
        return DeliveryAddressDto.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public List<DeliveryAddressDto> findAllByUserId(Long userId) {
        List<DeliveryAddress> addresses = deliveryAddressRepository.findAllByUserId(userId);
        return addresses.stream()
                .map(DeliveryAddressDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public DeliveryAddressDto findByIdAndUserId(Long id, Long userId) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR));
        return DeliveryAddressDto.fromEntity(deliveryAddress);
    }

    @Transactional
    public DeliveryAddressDto update(Long id, Long userId, String postcode, String address, String detailAddress,
                                     String recipient, String phoneNumber) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR));

        deliveryAddress.update(postcode, address, detailAddress, recipient, phoneNumber);
        DeliveryAddress saved = deliveryAddressRepository.save(deliveryAddress);
        return DeliveryAddressDto.fromEntity(saved);
    }

    @Transactional
    public void delete(Long id, Long userId) {
        if (!deliveryAddressRepository.existsByIdAndUserId(id, userId)) {
            throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
        deliveryAddressRepository.deleteById(id);
    }

    @Transactional
    public DeliveryAddressDto deliveryAddressDefault(Long id, Long userId) {
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR));

        // 기존 기본 배송지 해제
        deliveryAddressRepository.clearDefaultByUserId(userId);

        // 새로운 기본 배송지 설정
        deliveryAddress.updateDefault(true);
        DeliveryAddress saved = deliveryAddressRepository.save(deliveryAddress);
        return DeliveryAddressDto.fromEntity(saved);
    }
}
