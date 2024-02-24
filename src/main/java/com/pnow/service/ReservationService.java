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

        LocalTime startTime = openingTime; //예약가능한 시작시간 오픈시간으로 default

        if (reservationDate.isEqual(LocalDate.now())) { //예약한 날짜가 오늘 날짜이면 startTime 변경
            LocalTime currentTime = LocalTime.now();
            int currentHour = currentTime.getHour();     //현재 시
            int currentMinute = currentTime.getMinute(); //현재 분

            //현재 분이 0분~29분이면 예약가능한시간을 현재시:30 으로 셋팅
            if (currentMinute >= 0 && currentMinute < 30) {
                startTime = LocalTime.of(currentHour, 30);
            } else {    //현재분이 30분~59분이면 예약가능한시간을 현재시+1:00 으로 셋팅
                startTime = LocalTime.of(currentHour + 1, 0);
            }
        }


        while (startTime.isBefore(closingTime)) { // 클로징 시간-30분 까지만 예약 가능시간에 add
            availableTimes.add(new ReservationAbleTimeDTO(startTime.format(DateTimeFormatter.ofPattern("HH:mm"))));
            startTime = startTime.plusMinutes(30);
        }

        return availableTimes;
    }
}
