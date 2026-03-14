package com.pnow.controller;

import com.pnow.aop.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class HomeController {

    /*
     * 홈 접속
     *
     * GET /
     * return "home"
     * */
    @GetMapping("/")
    @LogExecutionTime
    public String root() {
        return "home";
    }
}
