package com.pnow.service;

import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.Reservation.ReservationStatus;
import com.pnow.domain.Store;
import com.pnow.domain.user.User;
import com.pnow.dto.ReservationAbleTimeDTO;
import com.pnow.dto.ReservationRequestDTO;
import com.pnow.repository.ReservationRepository;
import com.pnow.repository.StoreRepository;
import com.pnow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ReservationAbleTimeDTO> findReservationAbleTimeDTOList(Long storeId, LocalDate reservationDate) {
        // 오픈 시간, 종료 시간
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + storeId));

        LocalTime openingTime = store.getOpeningTime();
        LocalTime closingTime = store.getClosingTime();

        // 해당 날짜에 예약한 시간들을 HashSet에 저장,  시간복잡도: O(1)
        List<Reservation> reservations = reservationRepository.findByStoreIdAndReservationDateAndReservationStatus(storeId, reservationDate, ReservationStatus.WAITING);
        Set<LocalTime> reservedTimes = new HashSet<>();
        for (Reservation reservation : reservations) {
            reservedTimes.add(reservation.getReservationTime());
        }

        // 예약 가능한 시간을 저장할 List
        List<ReservationAbleTimeDTO> availableTimes = new ArrayList<>();

        LocalTime startTime = openingTime; // 예약 가능한 시작시간을 오픈시간으로 초기화
        LocalTime currentTime = LocalTime.now();

        //예약날짜가 오늘날짜인데 오픈시간이후면 startTime(예약가능 시작시간) 변경
        if (reservationDate.isEqual(LocalDate.now()) && currentTime.isAfter(openingTime)) { //reservationDate.isEqual(LocalDate.now())만 하면 계속 현재시간부터 시작함
            int currentMinute = currentTime.getMinute();
            int currentHour = currentTime.getHour();

            // 현재 분이 0분~29분이면 예약 가능한 시간을 현재시:30 으로 설정
            if (currentMinute >= 0 && currentMinute < 30) {
                startTime = LocalTime.of(currentHour, 30);
            } else { // 현재 분이 30분~59분이면 예약 가능한 시간을 (현재시+1):00 으로 설정
                startTime = LocalTime.of(currentHour + 1, 0);
            }
        }

        // 예약 가능한 시간을 계산하여 availableTimes에 추가
        while (startTime.isBefore(closingTime)) { //HashSet contains로 List보다 더 시간복잡도 줄임
            if (!reservedTimes.contains(startTime)) {
                availableTimes.add(new ReservationAbleTimeDTO(startTime.format(DateTimeFormatter.ofPattern("HH:mm"))));
            }
            startTime = startTime.plusMinutes(30); // 다음 시간으로 이동
        }

        return availableTimes;
    }


    @Transactional
    public void makeReservation(ReservationRequestDTO requestDTO, SessionUserDTO sessionUserDTO) {
        Store store = storeRepository.findById(requestDTO.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + requestDTO.getStoreId()));
        User user = userRepository.findById(sessionUserDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + sessionUserDTO.getId()));


        //Reservation 엔티티 셋팅
        Reservation reservation = new Reservation();
        reservation.setReservationDate(requestDTO.getSelectedDate());
        reservation.setReservationTime(requestDTO.getSelectedTime());
        reservation.setGuestCount(requestDTO.getNumberOfPeople());
        reservation.setUser(user);
        reservation.setStore(store);

        //예약 저장
        reservationRepository.save(reservation);

    }

}
