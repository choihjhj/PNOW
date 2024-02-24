package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
public class HomeController {

    private final ReservationService reservationService;

    /*
     * 홈 접속
     * GET /
     * return "home"
     * */
    @GetMapping("/")
    public String root(@LoginUser SessionUserDTO user) {
        log.info("root 메소드 진입 user = {}", user);
        if(user != null){

        }
        return "home";
//        return "temp";
    }

}
