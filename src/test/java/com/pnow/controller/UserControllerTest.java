package com.pnow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnow.config.auth.dto.SessionUserDTO;
import com.pnow.domain.user.Role;
import com.pnow.domain.user.User;
import com.pnow.dto.UserUpdateDto;
import com.pnow.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private SessionUserDTO sessionUser;

    @BeforeEach
    void setup() {
        User loginUser = new User("홍길동", "test@example.com", "pic.png", Role.USER);
        loginUser.setId(1L);
        sessionUser = new SessionUserDTO(loginUser);

        // SecurityContextHolder에 인증 객체 직접 넣기
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        sessionUser,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("로그인한 사용자 ID와 요청 ID가 다르면 403 Forbidden")
    void idTest() throws Exception {
        Long requestId = 2L; // 로그인 사용자와 다른 ID

        UserUpdateDto dto = new UserUpdateDto();
        dto.setName("변경된 이름");

        mvc.perform(put("/users/" + requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden()); // 403 확인
    }

    @Test
    @DisplayName("회원 정보 수정 @Valid 유효성 검사")
    void editUserTest() throws Exception {
        Long requestId = 1L; // 로그인 사용자와 같은 ID

        UserUpdateDto dto = new UserUpdateDto();
        dto.setName(null); // @Valid 검증 실패 유도

        mvc.perform(put("/users/" + requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()); // 400 확인
    }
}
