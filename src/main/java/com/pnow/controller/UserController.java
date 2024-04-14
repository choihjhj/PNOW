package com.pnow.controller;

import com.pnow.config.auth.LoginUser;
import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.dto.UserUpdateDto;
import com.pnow.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("/users")
@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {
    private final UserService userService;

    /*
     * 내정보 페이지 이동 myinfo.html
     * - 유저정보 model에 저장
     * GET /users
     * return "/users/myinfo"
     * */
    @GetMapping
    public String userMyinfo(@LoginUser SessionUserDTO user, Model model) {
        log.info("내정보 페이지 진입 user={}", user.getName());
        model.addAttribute("user", userService.findUser(user));
        return "users/myinfo";
    }


    /*
     * 회원 정보 수정
     * PUT /users/{id}
     * */
    @PutMapping("/{id}")
    public ResponseEntity<String> editUser(@PathVariable("id") Long id,
                                           @Valid @RequestBody UserUpdateDto userUpdateDto) {
        log.info("내정보 수정 메소드 진입 id={}, data={}", id, userUpdateDto.getName());
        userService.updateUser(id, userUpdateDto);
        return ResponseEntity.ok("회원 정보가 업데이트되었습니다.");
    }

    /*
     * 회원 탈퇴
     * DELETE /users/{id}
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        log.info("회원 탈퇴 메소드 진입 id={}", id);

        userService.deleteUser(id);

        // 로그아웃 처리(현재 사용자의 보안 컨텍스트에서 인증을 제거하고 필요한 경우 세션을 무효화)
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return ResponseEntity.ok("회원탈퇴가 완료되었습니다.");

    }

}
