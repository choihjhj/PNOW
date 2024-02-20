package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUser;
import com.pnow.domain.Category;
import com.pnow.domain.City;
import com.pnow.service.CategoryService;
import com.pnow.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final CategoryService categoryService;
    private final CityService cityService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String root(@LoginUser SessionUser user) {
        //topbar, sidebar에서 공용으로 쓸 데이터(categoryList, cityList) 세션에 저장
        List<Category> categoryList = categoryService.getCategoryList();
        List<City> cityList = cityService.getCityList();

        httpSession.setAttribute("categoryList", categoryList);
        httpSession.setAttribute("cityList",cityList);

        return "home";
//        return "temp2";
    }

}
