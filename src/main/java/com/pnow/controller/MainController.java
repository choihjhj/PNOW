package com.pnow.controller;

import com.pnow.domain.CategoryType;
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
        model.addAttribute("CategoryType", CategoryType.values());
        return "home";
    }

}
