package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {
    private final UserService userService;

    /*
     * 내정보로 페이지 이동 myinfo.html
     * - 유저정보 model에 저장
     * GET /users
     * return "/users/myinfo"
     * */
    @GetMapping
    public String userMyinfo(@LoginUser SessionUserDTO user, Model model){
        log.info("내정보 페이지 진입 user={}",user.getName());
        if(user != null){
            model.addAttribute("user", userService.findUser(user));
        }
        return "users/myinfo";
    }

}
