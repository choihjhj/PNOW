package com.pnow.controller;

import com.pnow.aop.LogExecutionTime;
import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.Reservation.ReservationStatus;
import com.pnow.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@Slf4j
public class HomeController {
    private final ReservationService reservationService;
    private final HttpSession httpSession;

    /*
     * 홈 접속
     * - 로그인한 유저 예약리스트 저장
     * GET /
     * return "home"
     * */
    @GetMapping("/")
    @LogExecutionTime
    public String root(@LoginUser SessionUserDTO user) {
        log.info("root 메소드 진입 user = {}", user);

        if(user != null){
            // 세션에 로그인한 유저 예약리스트 저장(home.html에서 fragment로 예약목록 topbar 사용할거라서)
            // model 아닌 세션 저장 이유: 모델은 일회성이라 페이지별로 공유 안됨,요청마다 model.addAttribute()를 반복하는 대신 HttpSession에 한 번 넣어두고 재활용하는 방식
            httpSession.setAttribute("reservationList",reservationService.findReservation(user, ReservationStatus.WAITING));
        }

        return "home";
    }
}
