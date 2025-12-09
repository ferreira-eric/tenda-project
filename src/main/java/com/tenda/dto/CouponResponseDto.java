package com.tenda.dto;

import com.tenda.entity.CouponStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
public class CouponResponseDto {

    private UUID id;
    private String code;
    private String description;
    private BigDecimal discountValue;
    private OffsetDateTime expirationDate;
    private CouponStatus status;
    private boolean published;
    private boolean redeemed;
}
