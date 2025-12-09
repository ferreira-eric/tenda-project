package com.tenda.rest.controller;

import com.tenda.dto.CouponRequestDto;
import com.tenda.dto.CouponResponseDto;
import com.tenda.rest.api.CouponApi;
import com.tenda.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CouponController implements CouponApi {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @Override
    public ResponseEntity<CouponResponseDto> create(CouponRequestDto request) {
        CouponResponseDto response = couponService.createCoupon(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CouponResponseDto> getById(UUID id) {
        CouponResponseDto response = couponService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        couponService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
