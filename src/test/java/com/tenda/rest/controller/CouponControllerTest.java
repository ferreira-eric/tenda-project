package com.tenda.rest.controller;

import com.tenda.dto.CouponRequestDto;
import com.tenda.dto.CouponResponseDto;
import com.tenda.service.CouponService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CouponControllerTest {

    @Mock
    private CouponService couponService;

    @InjectMocks
    private CouponController controller;

    @Test
    void createReturnsOkAndBody() {
        CouponRequestDto request = mock(CouponRequestDto.class);
        CouponResponseDto response = mock(CouponResponseDto.class);

        when(couponService.createCoupon(request)).thenReturn(response);

        ResponseEntity<CouponResponseDto> result = controller.create(request);

        assertEquals(200, result.getStatusCode().value());
        assertSame(response, result.getBody());
        verify(couponService, times(1)).createCoupon(request);
    }

    @Test
    void getByIdReturnsOkAndBody() {
        UUID id = UUID.randomUUID();
        CouponResponseDto response = mock(CouponResponseDto.class);

        when(couponService.getById(id)).thenReturn(response);

        ResponseEntity<CouponResponseDto> result = controller.getById(id);

        assertEquals(200, result.getStatusCode().value());
        assertSame(response, result.getBody());
        verify(couponService, times(1)).getById(id);
    }

    @Test
    void deleteReturnsNoContent() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> result = controller.delete(id);

        assertEquals(204, result.getStatusCode().value());
        assertNull(result.getBody());
        verify(couponService, times(1)).deleteById(id);
    }
}