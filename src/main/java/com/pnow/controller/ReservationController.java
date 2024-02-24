package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.dto.ReservationAbleTimeDTO;
import com.pnow.dto.ReservationRequestDTO;
import com.pnow.dto.StoreDTO;
import com.pnow.service.ReservationService;
import com.pnow.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;
    private final StoreService storeService;

    /*
     * reservation.html 예약 페이지 접속
     * - 가게 조회 후 model에 저장
     * GET /reservation/{storeId}
     * return "/reservation/reservation"
     * */
    @GetMapping("/{storeId}")
    public String reservation( @PathVariable("storeId") Long storeId, Model model, @LoginUser SessionUserDTO user){
        log.info("예약페이지로 이동하는 /reservation/{storeId} get 메소드 진입 storeId = {}", storeId);

        if (user != null) {
            StoreDTO storeDTO = storeService.findStoreDTO(storeId);
            model.addAttribute("store", storeDTO);
            return "reservation/reservation";
        } else {
            throw new IllegalStateException("예약 가능 시간 조회를 위해서는 로그인이 필요합니다.");
        }
    }

    /*
     * 예약 가능 시간 조회
     * GET /reservation/{storeId}/{reservationDate}
     * return List<ReservationAbleTimeDTO>
     * */
    @GetMapping("/{storeId}/{reservationDate}")
    @ResponseBody
    public List<ReservationAbleTimeDTO> reservationTimeRead(@PathVariable("storeId") Long storeId,
                                                            @PathVariable("reservationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate reservationDate,
                                                            @LoginUser SessionUserDTO user) {
        if (user != null) {
            log.info("예약 가능 시간 조회 메소드 진입 storeId = {}, reservationDate = {}", storeId, reservationDate);
            return reservationService.findReservationAbleTimeDTOList(storeId, reservationDate);
        } else {
            throw new IllegalStateException("예약 가능 시간 조회를 위해서는 로그인이 필요합니다.");
        }
    }

    /*
     * 예약 작성
     * POST /reservation
     *
     * */
    @PostMapping
    @ResponseBody
    public void makeReservation(@RequestBody ReservationRequestDTO requestDto, @LoginUser SessionUserDTO user) {
        log.info("로그인 객체 user = {}", user);
        log.info("예약 작성 메서드 진입 storeId={}, 예약날짜={}, 예약시간={}, 인원수={}", requestDto.getStoreId(), requestDto.getSelectedDate(), requestDto.getSelectedTime(), requestDto.getNumberOfPeople());

        if (user != null) {
            reservationService.makeReservation(requestDto, user);
        } else {
            throw new IllegalStateException("예약을 위해서는 로그인이 필요합니다.");
        }
    }

}
