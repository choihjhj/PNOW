package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.Reservation.ReservationStatus;
import com.pnow.dto.ReservationAbleTimeDto;
import com.pnow.dto.ReservationRequestDto;
import com.pnow.dto.StoreDto;
import com.pnow.service.ReservationService;
import com.pnow.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reservations")
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;
    private final StoreService storeService;
    private final HttpSession httpSession;

    /*
     * reservation.html 예약 페이지 이동
     * - 가게 조회 후 model에 저장
     * GET /reservations/stores/{storeId}
     * return "/reservations/reservation"
     * */
    @GetMapping("/stores/{storeId}")
    public String reservation( @PathVariable("storeId") Long storeId, Model model){
        log.info("예약페이지로 이동하는 /reservation/stores/{storeId} get 메소드 진입 storeId = {}", storeId);
        StoreDto storeDTO = storeService.findStoreDTO(storeId);
        model.addAttribute("store", storeDTO);
        return "reservations/reservation";
    }

    /*
     * 예약 가능 시간 목록 조회
     * GET /reservations/{storeId}/availability/{reservationDate}
     * return List<ReservationAbleTimeDTO>
     * */
    @GetMapping("/{storeId}/availability/{reservationDate}")
    @ResponseBody
    public List<ReservationAbleTimeDto> getReservationTime(@PathVariable("storeId") Long storeId,
                                                           @PathVariable("reservationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate reservationDate) {
        log.info("예약 가능 시간 조회 메소드 진입 storeId = {}, reservationDate = {}", storeId, reservationDate);
        return reservationService.findReservationAbleTimeDTOList(storeId, reservationDate);
    }

    /*
     * 예약 등록
     * POST /reservations
     *
     * */
    @PostMapping
    @ResponseBody
    public void createReservation(@Valid @RequestBody ReservationRequestDto requestDto, @LoginUser SessionUserDTO user) {
        log.info("로그인 객체 user = {}", user);
        log.info("예약 등록 메서드 진입 storeId={}, 예약날짜={}, 예약시간={}, 인원수={}", requestDto.getStoreId(), requestDto.getSelectedDate(), requestDto.getSelectedTime(), requestDto.getNumberOfPeople());
        reservationService.makeReservation(requestDto, user);
    }

    /*
     * 예약 목록 조회
     * GET /reservations/list
     * return "/reservations/reservationList"
     * */
    @GetMapping("/list")
    public  String getReservation(@LoginUser SessionUserDTO user){
        log.info("예약 목록 조회 메소드 진입 user = {}", user);
        httpSession.setAttribute("reservationList",reservationService.findReservation(user, ReservationStatus.WAITING));
        return "reservations/reservationList";
    }

    /*
     * 예약 삭제
     * DELETE /reservations/{id}
     *
     * */
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteReservation( @PathVariable("id") Long id){
        log.info("예약 삭제 메소드 진입 reservationId = {}", id);
        reservationService.cancelReservation(id);
    }

    /*
     * 예약 완료 목록 조회
     * GET /reservations/past
     * return "/reservations/reservationPastList"
     * */
    @GetMapping("/past")
    public String getPastReservation(@LoginUser SessionUserDTO user, Model model){
        log.info("지난 예약 목록 조회 메소드 진입 user = {}", user.getName());
        model.addAttribute("reservationPastList",reservationService.findReservation(user, ReservationStatus.COMPLETE));
        return "reservations/reservationPastList";
    }
}
