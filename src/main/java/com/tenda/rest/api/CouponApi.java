package com.tenda.rest.api;

import com.tenda.dto.CouponRequestDto;
import com.tenda.dto.CouponResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Coupons", description = "Operations for coupon management")
@RequestMapping("/coupon")
public interface CouponApi {

    @Operation(summary = "Create a new Coupon")
    @PostMapping
    ResponseEntity<CouponResponseDto> create(@Valid @RequestBody CouponRequestDto request);

    @Operation(summary = "Find a Coupon by ID")
    @GetMapping("/{id}")
    ResponseEntity<CouponResponseDto> getById(@PathVariable UUID id);

    @Operation(summary = "Delete a Coupon by ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
