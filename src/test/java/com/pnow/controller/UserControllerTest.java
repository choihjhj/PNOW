package com.pnow.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnow.config.auth.CustomOAuth2UserService;
import com.pnow.config.auth.dto.CustomUserPrincipal;
import com.pnow.domain.user.Role;
import com.pnow.domain.user.User;
import com.pnow.dto.UserUpdateDto;
import com.pnow.service.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // SecurityConfig 생성자 의존성 해결
    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private ObjectMapper objectMapper; //JSON 직렬화

    @Test
    @DisplayName("내정보 조회 성공")
    void 내정보_조회() throws Exception {
        String username = "testUser";

        // 뷰 이름만 검증, 실제 HTML 파일은 필요 없음
        mockMvc.perform(get("/users")
                        .with(SecurityMockMvcRequestPostProcessors.user(username).roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("users/myinfo"));

        verify(userService).findUser(org.mockito.ArgumentMatchers.any());
    }

    @Test
    @DisplayName("회원 정보 수정 성공")
    void 회원_정보_수정() throws Exception {
    	
    	// 테스트용 로그인 사용자
        User loginUser = new User("홍길동", "test@example.com", "pic.png", Role.USER);
        loginUser.setId(3L); // 테스트할 ID
        
        CustomUserPrincipal principal = new CustomUserPrincipal();
        principal.setId(loginUser.getId());
        principal.setName(loginUser.getName());
        principal.setEmail(loginUser.getEmail());
        principal.setPicture(loginUser.getPicture());
        principal.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")));


        // 요청 DTO
        UserUpdateDto dto = new UserUpdateDto();
        dto.setName("newName");

        mockMvc.perform(put("/users/{id}", 3L)
                        .with(SecurityMockMvcRequestPostProcessors.authentication(
                                new UsernamePasswordAuthenticationToken(
                                        principal, null, principal.getAuthorities()
                                )
                        ))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("회원 정보가 업데이트되었습니다."));

        // 서비스 호출 검증
        verify(userService).updateUser(anyLong(), any(UserUpdateDto.class));
    }

    @Test
    @DisplayName("회원 탈퇴 성공")
    void 회원_탈퇴() throws Exception {
    	// 테스트용 로그인 사용자
        User loginUser = new User("홍길동", "test@example.com", "pic.png", Role.USER);
        loginUser.setId(3L); // 테스트할 ID
        
        CustomUserPrincipal principal = new CustomUserPrincipal();
        principal.setId(loginUser.getId());
        principal.setName(loginUser.getName());
        principal.setEmail(loginUser.getEmail());
        principal.setPicture(loginUser.getPicture());
        principal.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")));


        mockMvc.perform(delete("/users/{id}", 3L)
        		.with(SecurityMockMvcRequestPostProcessors.authentication(
                        new UsernamePasswordAuthenticationToken(
                                principal, null, principal.getAuthorities()
                        )
                 )))
                .andExpect(status().isOk())
                .andExpect(content().string("회원탈퇴가 완료되었습니다."));

        verify(userService).deleteUser(org.mockito.ArgumentMatchers.anyLong());
    }
	
}