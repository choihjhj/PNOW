package com.pnow.service;

import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.Reservation.ReservationStatus;
import com.pnow.domain.Store;
import com.pnow.domain.user.User;
import com.pnow.dto.ReservationAbleTimeDTO;
import com.pnow.dto.ReservationDetailDTO;
import com.pnow.dto.ReservationRequestDTO;
import com.pnow.repository.ReservationRepository;
import com.pnow.repository.StoreRepository;
import com.pnow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public List<ReservationAbleTimeDTO> findReservationAbleTimeDTOList(Long storeId, LocalDate reservationDate) {
        // 오픈 시간, 종료 시간
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException("Store not found" + storeId));

        LocalTime openingTime = store.getOpeningTime();
        LocalTime closingTime = store.getClosingTime();


        List<Reservation> reservations = reservationRepository.findByStoreIdAndReservationDateAndReservationStatus(storeId, reservationDate, ReservationStatus.WAITING);
        Set<LocalTime> reservedTimes = new HashSet<>();
        for (Reservation reservation : reservations) { // 레포지토리에서 받아온 해당 날짜에 이미 예약한 시간들을 HashSet에 따로 저장(아래에서 예약가능시간에서 제외시킬것임)
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
        while (startTime.isBefore(closingTime)) {
            if (!reservedTimes.contains(startTime)) { //HashSet contains로 List보다 더 시간복잡도  줄임 O(1)
                availableTimes.add(new ReservationAbleTimeDTO(startTime.format(DateTimeFormatter.ofPattern("HH:mm"))));
            }
            startTime = startTime.plusMinutes(30); // 다음 시간으로 이동
        }

        return availableTimes;
    }

    //예약 추가
    @Transactional
    public void makeReservation(ReservationRequestDTO requestDTO, SessionUserDTO sessionUserDTO) {
        Store store = storeRepository.findById(requestDTO.getStoreId())
                .orElseThrow(() -> new EntityNotFoundException("Store not found" + requestDTO.getStoreId()));
        User user = userRepository.findById(sessionUserDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found" + sessionUserDTO.getId()));


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

    //회원의 예약 목록 조회
    //회원의 지난 예약 목록 조회
    @Transactional(readOnly = true)
    public List<ReservationDetailDTO> findReservation(SessionUserDTO sessionUserDTO, ReservationStatus status){

        User user = userRepository.findById(sessionUserDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found" + sessionUserDTO.getId()));

        //user에 해당하는 예약 목록 조회
        List<Reservation> reservationList = reservationRepository.findAllByUserAndReservationStatusOrderByReservationDateAscReservationTimeAsc(user, status);
        return reservationList.stream().map(this::mapToReservationDetailDTO)
                .collect(Collectors.toList());

    }

    private ReservationDetailDTO mapToReservationDetailDTO(Reservation reservation) {
        ReservationDetailDTO dto = new ReservationDetailDTO();
        dto.setId(reservation.getId());                                 //예약id
        dto.setStoreName(reservation.getStore().getStoreName());        //가게이름
        dto.setSelectedDate(reservation.getReservationDate());          //예약날짜
        dto.setSelectedTime(reservation.getReservationTime());          //예약시간
        dto.setNumberOfPeople(reservation.getGuestCount());             //인원수
        dto.setReservationStatus(reservation.getReservationStatus());   //예약상태
        log.info("예약상태: {}",dto.getReservationStatus());
        dto.setCreatedDate(formatTime(reservation.getCreatedDate()));   //예약접수일

        return dto;
    }
    private String formatTime(LocalDateTime time) { return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm")); }

    //예약 삭제
    @Transactional
    public void cancelReservation(Long id){
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
        reservationRepository.delete(reservation);
    }




}
