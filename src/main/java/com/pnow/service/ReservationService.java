package com.pnow.service;

import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.Reservation.ReservationStatus;
import com.pnow.domain.Store;
import com.pnow.domain.user.User;
import com.pnow.dto.ReservationAbleTimeDto;
import com.pnow.dto.ReservationDto;
import com.pnow.dto.ReservationRequestDto;
import com.pnow.repository.ReservationRepository;
import com.pnow.repository.StoreRepository;
import com.pnow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    //시간 지난 예약들의 status 업데이트(WAITING->COMPLETE)
    @Transactional
    public void updateUserReservationStatus(){
        log.info("예약상태 업데이트 서비스 진입");
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // 현재 날짜와 시간 이전의 예약목록 가져와서 예약 상태 변경
        List<Reservation> reservationsToUpdate = reservationRepository
                .findByReservationDateBeforeOrReservationDateAndReservationTimeBeforeAndReservationStatus(
                currentDate, currentDate, currentTime, ReservationStatus.WAITING);

        reservationsToUpdate.forEach(reservation -> reservation.setReservationStatus(ReservationStatus.COMPLETE));

        reservationRepository.saveAll(reservationsToUpdate);
    }

    //선택한 날짜에 예약 가능 시간 목록 조회
    @Transactional(readOnly = true)
    public List<ReservationAbleTimeDto> findReservationAbleTimeDTOList(Long storeId, LocalDate reservationDate) {
        // 오픈 시간, 종료 시간
        Store store = findByIdOrThrow(storeRepository, storeId, "StoreId");

        LocalTime openingTime = store.getOpeningTime();
        LocalTime closingTime = store.getClosingTime();


        List<Reservation> reservations = reservationRepository.findByStoreIdAndReservationDateAndReservationStatus(storeId, reservationDate, ReservationStatus.WAITING);
        Set<LocalTime> reservedTimes = new HashSet<>();
        for (Reservation reservation : reservations) { // 레포지토리에서 받아온 해당 날짜에 이미 예약한 시간들을 HashSet에 따로 저장(아래에서 예약가능시간에서 제외시킬것임)
            reservedTimes.add(reservation.getReservationTime());
        }

        // 예약 가능한 시간을 저장할 List
        List<ReservationAbleTimeDto> availableTimes = new ArrayList<>();

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
        while (startTime.isBefore(closingTime)) {
            if (!reservedTimes.contains(startTime)) { //HashSet contains로 List보다 더 시간복잡도  줄임 O(1)
                availableTimes.add(new ReservationAbleTimeDto(startTime.format(DateTimeFormatter.ofPattern("HH:mm"))));
            }
            startTime = startTime.plusMinutes(30); // 다음 시간으로 이동
        }

        return availableTimes;
    }

    //예약 추가
    @Transactional
    public void makeReservation(ReservationRequestDto requestDTO, SessionUserDTO sessionUserDTO) {
        Store store = findByIdOrThrow(storeRepository, requestDTO.getStoreId(), "StoreId");
        User user = findByIdOrThrow(userRepository, sessionUserDTO.getId(), "UserId");

        //예약 저장
        reservationRepository.save(toEntity(requestDTO, user, store));

    }

    private Reservation toEntity(ReservationRequestDto requestDto, User user, Store store) {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(requestDto.getSelectedDate());
        reservation.setReservationTime(requestDto.getSelectedTime());
        reservation.setGuestCount(requestDto.getNumberOfPeople());
        reservation.setUser(user);
        reservation.setStore(store);
        return reservation;
    }

    //전달된 ReservationStatus status에 해당하는 회원의 예약 목록 조회(WAITING, COMPLETE)
    @Transactional(readOnly = true)
    public List<ReservationDto> findReservation(SessionUserDTO sessionUserDTO, ReservationStatus status){

        User user = findByIdOrThrow(userRepository, sessionUserDTO.getId(), "UserId");

        //user에 해당하는 예약 목록 조회
        List<Reservation> reservationList = reservationRepository.findAllByUserAndReservationStatusOrderByReservationDateAscReservationTimeAsc(user, status);

        return reservationList.stream().map(ReservationDto::new)
                .collect(Collectors.toList());

    }

    //예약 삭제
    @Transactional
    public void cancelReservation(Long id){
        Reservation reservation = findByIdOrThrow(reservationRepository, id, "ReservationId");
        reservationRepository.delete(reservation);
    }

    private <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found : " + id));
    }


}
