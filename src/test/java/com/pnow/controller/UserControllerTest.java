package com.pnow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnow.dto.UserUpdateDto;
import com.pnow.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(controllers = UserControllerTest.class)
/*
- JPA 기능은 동작하지 않는다.
- @Controller, @ControllerAdvice 사용 가능
- 단, @Service, @Repository등은 사용할 수 없다.
*/
class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;
    /**
     * 웹 API 테스트할 때 사용
     * 스프링 MVC 테스트의 시작점
     * HTTP GET,POST 등에 대해 API 테스트 가능
     * */


    @Test
    @DisplayName("회원 정보 수정 api 유효성 검사")
    void editUserTest() throws Exception{
        //given
        String id="Test"; //IllegalStateException
        String name = null; //MethodArgumentNotValidException
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setName(name);

        //when

        //then
        mvc.perform(put("/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userUpdateDto)))
                .andExpect(status().isBadRequest());

    }

}