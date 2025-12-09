package com.tenda.mapper;


import com.tenda.dto.CouponRequestDto;
import com.tenda.dto.CouponResponseDto;
import com.tenda.entity.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "redeemed", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Coupon toEntity(CouponRequestDto request);

    CouponResponseDto toResponse(Coupon coupon);
}
