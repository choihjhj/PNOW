package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/bookmarks")
@RequiredArgsConstructor
@RestController
@Slf4j
public class BookmarkController {
    private final BookmarkService bookmarkService;

    /*
     * 즐겨찾기 삭제
     * DELETE /bookmarks/{storeId}
     *
     * */
    @DeleteMapping("/{storeId}")
    public void deleteBookmark(@PathVariable("storeId") Long storeId,@LoginUser SessionUserDTO user){
        log.info("즐겨찾기 삭제 메소드 진입 storeId = {}", storeId);
        if(user != null){
            bookmarkService.cancelBookmark(storeId,user.getId());
        }
    }

    /*
     * 즐겨찾기 등록
     * POST /bookmarks/{storeId}
     *
     * */
    @PostMapping("/{storeId}")
    public void createBookmark(@PathVariable("storeId") Long storeId, @LoginUser SessionUserDTO userDTO){
        log.info("즐겨찾기 등록 메소드 진입 storeId = {}", storeId);
        if (userDTO != null) {
            bookmarkService.makeBookmark(storeId, userDTO);
        } else {
            throw new IllegalStateException("즐겨찾기를 위해서는 로그인이 필요합니다.");
        }
    }
}
