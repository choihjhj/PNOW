package com.pnow.service;

import com.pnow.domain.Reservation;
import com.pnow.domain.ReservationStatus;
import com.pnow.domain.Store;
import com.pnow.dto.ReservationAbleTimeDTO;
import com.pnow.repository.ReservationRepository;
import com.pnow.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public List<ReservationAbleTimeDTO> findReservationAbleTimeDTOList(Long storeId, LocalDate reservationDate) {
        // 오픈 시간, 종료 시간
        Optional<Store> store = storeRepository.findById(storeId);
        LocalTime openingTime = store.get().getOpeningTime();
        LocalTime closingTime = store.get().getClosingTime();

        // 해당 날짜에 예약한 시간들
        List<Reservation> reservations = reservationRepository.findByStoreIdAndReservationDateAndReservationStatus(storeId, reservationDate, ReservationStatus.WAITING);

        List<ReservationAbleTimeDTO> availableTimes = new ArrayList<>(); // 예약 가능 시간 저장할 List
        LocalTime startTime = openingTime; // 예약 가능한 시간 시작시간은 오픈 시간부터

        while (startTime.isBefore(closingTime)) { // 클로징 시간-30분 까지만 예약 가능시간에 add
            availableTimes.add(new ReservationAbleTimeDTO(startTime.format(DateTimeFormatter.ofPattern("HH:mm"))));
            startTime = startTime.plusMinutes(30);
        }

        return availableTimes;
    }
}
