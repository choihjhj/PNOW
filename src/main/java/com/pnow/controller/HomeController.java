package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.user.User;
import com.pnow.service.ReservationService;
import com.pnow.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@Slf4j
public class HomeController {
    private final ReservationService reservationService;
    private final UserService userService;
    private final HttpSession httpSession;

    /*
     * 홈 접속
     * - 세션에 사용자 정보 업데이트, 로그인한 유저 예약리스트 저장
     * GET /
     * return "home"
     * */
    @GetMapping("/")
    public String root(@LoginUser SessionUserDTO user, Model model) {
        log.info("root 메소드 진입 user = {}", user);
        if(user != null){
            // 업데이트된 사용자 정보 가져오기
            User updatedUser = userService.findUser(user);
            // 세션에 업데이트된 사용자 정보 저장
            httpSession.setAttribute("user", new SessionUserDTO(updatedUser));
            // 세션에 로그인한 유저 예약리스트 저장
            httpSession.setAttribute("reservationList",reservationService.findReservation(user));
        }
        return "home";
    }
}
