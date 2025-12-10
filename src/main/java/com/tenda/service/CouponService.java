package com.tenda.service;

import com.tenda.dto.CouponRequestDto;
import com.tenda.dto.CouponResponseDto;
import com.tenda.entity.Coupon;
import com.tenda.entity.CouponStatus;
import com.tenda.exception.BusinessException;
import com.tenda.exception.NotFoundException;
import com.tenda.mapper.CouponMapper;
import com.tenda.repository.CouponRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class CouponService {

    private final CouponRepository repository;
    private final CouponMapper mapper;

    public CouponService(CouponRepository repository, CouponMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public CouponResponseDto createCoupon(CouponRequestDto request) {
        validateExpirationDate(request.getExpirationDate());

        String couponCode = normalizeCode(request.getCode());
        validateCode(couponCode);

        var coupon = mapper.toEntity(request);
        coupon.setCode(couponCode);
        coupon.setPublished(request.getPublished() != null
                && request.getPublished());
        coupon.setRedeemed(false);
        coupon.setDeleted(false);
        coupon.setCreatedAt(OffsetDateTime.now());
        coupon.setUpdatedAt(OffsetDateTime.now());
        coupon.setStatus(CouponStatus.ACTIVE);

        return mapper.toResponse(repository.save(coupon));
    }

    @Transactional
    public CouponResponseDto getById(UUID id) {
        var coupon = repository.findById(id)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> new NotFoundException("Coupon not found"));
        updateStatus(coupon);
        return mapper.toResponse(coupon);
    }

    @Transactional
    public void deleteById(UUID id) {
        var coupon = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Coupon not found"));

        if (coupon.isDeleted()) {
            throw new BusinessException("Coupon already deleted");
        }
        coupon.setDeleted(true);
        coupon.setDeletedAt(OffsetDateTime.now());
        coupon.setStatus(CouponStatus.DELETED);
        repository.save(coupon);
    }

    private void validateExpirationDate(OffsetDateTime date) {
        if (date.toInstant().isBefore(Instant.now())) {
            throw new BusinessException("Expiration date cannot be in the past");
        }
    }

    private void updateStatus(Coupon coupon) {
        if (coupon.isDeleted()) {
            return;
        }

        coupon.setStatus(coupon.getExpirationDate().toInstant().isBefore(Instant.now()) ?
                CouponStatus.INACTIVE : CouponStatus.ACTIVE);
    }

    private String normalizeCode(String code) {
        String normalized = code.replaceAll("[^A-Za-z0-9]", "");
        if (normalized.length() != 6) {
            throw new BusinessException("Code must be exactly 6 alphanumeric characters");
        }
        return normalized.toUpperCase();
    }

    private void validateCode(String code) {
        var coupon = repository.findByCode(code);
        if (coupon.isPresent()) {
            throw new BusinessException("Coupon code already exists or have existed");
        }
    }
}
