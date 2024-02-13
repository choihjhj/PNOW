package com.pnow.controller;

import com.pnow.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@RequiredArgsConstructor
@Controller
public class MainController {

    private final StoreService storeService;

    @GetMapping("/")
    public String root(Model model) {
        
        return "index";

        //category 1에 해당하는 list DB에서 불러와서 model에 담기

        //return "redirect:/home";
    }

}
