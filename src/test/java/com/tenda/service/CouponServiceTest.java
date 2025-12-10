package com.tenda.service;

import com.tenda.dto.CouponRequestDto;
import com.tenda.dto.CouponResponseDto;
import com.tenda.entity.Coupon;
import com.tenda.entity.CouponStatus;
import com.tenda.exception.BusinessException;
import com.tenda.exception.NotFoundException;
import com.tenda.mapper.CouponMapper;
import com.tenda.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository repository;

    @Mock
    private CouponMapper mapper;

    @InjectMocks
    private CouponService service;

    @Test
    void createCouponSuccess() {
        var request = mock(CouponRequestDto.class);
        var future = OffsetDateTime.now().plusDays(1);
        when(request.getExpirationDate()).thenReturn(future);
        when(request.getCode()).thenReturn("ab-12c3");
        when(request.getPublished()).thenReturn(Boolean.TRUE);

        var entity = new Coupon();
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(any(Coupon.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any(Coupon.class))).thenReturn(mock(CouponResponseDto.class));

        service.createCoupon(request);

        ArgumentCaptor<Coupon> captor = ArgumentCaptor.forClass(Coupon.class);
        verify(repository).save(captor.capture());
        Coupon saved = captor.getValue();

        assertEquals("AB12C3", saved.getCode());
        assertTrue(saved.isPublished());
        assertFalse(saved.isRedeemed());
        assertFalse(saved.isDeleted());
        assertEquals(CouponStatus.ACTIVE, saved.getStatus());
    }

    @Test
    void createCouponExpirationInPastThrowsBusinessException() {
        var request = mock(CouponRequestDto.class);
        when(request.getExpirationDate()).thenReturn(OffsetDateTime.now().minusSeconds(1));

        assertThrows(BusinessException.class, () -> service.createCoupon(request));
        verifyNoInteractions(mapper);
        verifyNoInteractions(repository);
    }

    @Test
    void createCouponInvalidCodeThrowsBusinessException() {
        var request = mock(CouponRequestDto.class);
        when(request.getExpirationDate()).thenReturn(OffsetDateTime.now().plusDays(1));
        when(request.getCode()).thenReturn("abc");

        when(mapper.toEntity(request)).thenReturn(new Coupon());

        assertThrows(BusinessException.class, () -> service.createCoupon(request));
        verify(mapper).toEntity(request);
        verify(repository, never()).save(any());
    }

    @Test
    void getByIdUpdatesStatusToInactiveWhenExpired() {
        UUID id = UUID.randomUUID();
        var coupon = mock(Coupon.class);

        when(repository.findById(id)).thenReturn(Optional.of(coupon));
        when(coupon.isDeleted()).thenReturn(false);
        when(coupon.getExpirationDate()).thenReturn(OffsetDateTime.now().minusDays(1));
        when(mapper.toResponse(coupon)).thenReturn(mock(CouponResponseDto.class));

        service.getById(id);

        verify(coupon).setStatus(CouponStatus.INACTIVE);
        verify(mapper).toResponse(coupon);
    }

    @Test
    void getByIdNotFoundWhenDeletedThrowsNotFoundException() {
        UUID id = UUID.randomUUID();
        var coupon = mock(Coupon.class);

        when(repository.findById(id)).thenReturn(Optional.of(coupon));
        when(coupon.isDeleted()).thenReturn(true);

        assertThrows(NotFoundException.class, () -> service.getById(id));
    }

    @Test
    void deleteByIdSuccessMarksDeletedAndSaves() {
        UUID id = UUID.randomUUID();
        var coupon = mock(Coupon.class);

        when(repository.findById(id)).thenReturn(Optional.of(coupon));
        when(coupon.isDeleted()).thenReturn(false);

        service.deleteById(id);

        verify(coupon).setDeleted(true);
        verify(coupon).setDeletedAt(any(OffsetDateTime.class));
        verify(coupon).setStatus(CouponStatus.DELETED);
        verify(repository).save(coupon);
    }

    @Test
    void deleteByIdAlreadyDeletedThrowsBusinessException() {
        UUID id = UUID.randomUUID();
        var coupon = mock(Coupon.class);

        when(repository.findById(id)).thenReturn(Optional.of(coupon));
        when(coupon.isDeleted()).thenReturn(true);

        assertThrows(BusinessException.class, () -> service.deleteById(id));
        verify(repository, never()).save(any());
    }
}