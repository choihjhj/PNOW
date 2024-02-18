package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUser;
import com.pnow.domain.CategoryType;
import com.pnow.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final StoreService storeService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String root(Model model, @LoginUser SessionUser user) {
        model.addAttribute("CategoryType", CategoryType.values());

        if( user != null){
            model.addAttribute("user",user);
        }
        return "home";
    }

}
