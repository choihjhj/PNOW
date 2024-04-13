package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/bookmarks")
@RequiredArgsConstructor
@Controller
@Slf4j
public class BookmarkController {
    private final BookmarkService bookmarkService;

    /*
     * 즐겨찾기 삭제
     * DELETE /bookmarks/{id}
     *
     * */
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteBookmark(@PathVariable("id") Long id, @LoginUser SessionUserDTO user){
        log.info("즐겨찾기 삭제 메소드 진입 bookmarkId = {}", id);
        bookmarkService.cancelBookmark(id);
    }

    /*
     * 즐겨찾기 등록
     * POST /bookmarks/{storeId}
     *
     * */
    @PostMapping("/{storeId}")
    @ResponseBody
    public void createBookmark(@PathVariable("storeId") Long storeId, @LoginUser SessionUserDTO userDTO){
        log.info("즐겨찾기 등록 메소드 진입 storeId = {}", storeId);
        bookmarkService.makeBookmark(storeId, userDTO);
    }

    /*
     * 즐겨찾기 목록 조회
     * GET /bookmarks/list
     * return "/bookmarks/bookmarkList"
     * */
    @GetMapping("/list")
    public String getBookmarkList(@LoginUser SessionUserDTO userDTO, Model model){
        log.info("즐겨찾기 목록 조회 메소드 진입 user = {}", userDTO);
        model.addAttribute("bookmarkList",bookmarkService.findBookmarkWithUserId(userDTO));
        return "bookmarks/bookmarkList";
    }
}
