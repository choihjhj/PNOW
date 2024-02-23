package com.pnow.controller;

import com.pnow.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;

    /*
     * reservation.html 접속
     * - 가게 조회 후 model에 저장
     * GET /reservation/{storeId}
     * return "/reservation/reservation"
     * */
//    @GetMapping("/reservation/{storeId}")
}
