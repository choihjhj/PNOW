package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUser;
import com.pnow.domain.Category;
import com.pnow.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MainController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public String root(Model model, @LoginUser SessionUser user) {
        List<Category> categoryList = categoryService.categoryList();
        model.addAttribute("categoryList", categoryList);
        log.info("카테고리List 객체 : {}",categoryList);

        if( user != null){
            model.addAttribute("user",user);
        }
        return "home";
    }

}
