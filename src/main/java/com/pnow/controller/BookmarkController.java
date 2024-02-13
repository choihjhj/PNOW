package com.pnow.controller;

import com.pnow.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/bookmark")
@RequiredArgsConstructor
@Controller
@Slf4j
public class BookmarkController {
    private final BookmarkService bookmarkService;

    /*
    @GetMapping(value = "/question/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        return "question_detail";
    }
    */
}
