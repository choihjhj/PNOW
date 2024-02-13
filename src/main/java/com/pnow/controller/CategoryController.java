package com.pnow.controller;

import com.pnow.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/category")
@RequiredArgsConstructor
@Controller
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

//    @GetMapping("/list/{id}")
//    public String list(@PathVariable("id") Long id, Model model) {
//
//        return "home"; //카테고리 1의 list 내역이 나오는 메인페이지로 get요청
//    }
}
