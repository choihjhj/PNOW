package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUser;
import com.pnow.domain.Category;
import com.pnow.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MainController {

    private final CategoryService categoryService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String root(@LoginUser SessionUser user) {
        List<Category> categoryList = categoryService.categoryList();
        httpSession.setAttribute("categoryList", categoryList); //model이 아닌 세션에 저장
        return "home";
//        return "temp2";
    }

}
