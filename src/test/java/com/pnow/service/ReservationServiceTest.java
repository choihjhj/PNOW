package com.pnow.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pnow.domain.Store;
import com.pnow.domain.Reservation.ReservationStatus;
import com.pnow.dto.ReservationAbleTimeDto;
import com.pnow.repository.ReservationRepository;
import com.pnow.repository.StoreRepository;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

	@Mock
	ReservationRepository reservationRepository; //Mokito가 가짜 레포지토리객체 만듦

	@Mock
	StoreRepository storeRepository;

	@InjectMocks
	ReservationService reservationService; //가짜Mock 레포지토리객체를 서비스에 주입해서 서비스객체생성

	@Test
	@DisplayName("예약 가능 시간 목록 조회")
	void findReservationAbleTimeDTOList() {
		//storeId=53L에 2026-03-17 13:30에 실제 예약 있음, 이 시간 제외한 예약 가능 목록 조회 테스트

		// given
		Long storeId = 53L;
		LocalDate date = LocalDate.of(2026,3,17);

		Store store = Store.builder().id(storeId)
		        .openingTime(LocalTime.of(10,0))
		        .closingTime(LocalTime.of(22,0))
		        .build();

		when(storeRepository.findById(storeId))
		.thenReturn(Optional.of(store));

		when(reservationRepository.findReservedTimes(
				storeId,
				date,
				ReservationStatus.WAITING))
		.thenReturn(List.of(LocalTime.of(13,30)));

		// when
		List<ReservationAbleTimeDto> result =
				reservationService.findReservationAbleTimeDTOList(storeId, date);

		// then
		result.forEach(r -> System.out.println(r.getReservationTime()));		
		assertThat(result).extracting("reservationTime")
		.contains("10:00","11:00","11:30")
		.doesNotContain("13:30");
	}



}
