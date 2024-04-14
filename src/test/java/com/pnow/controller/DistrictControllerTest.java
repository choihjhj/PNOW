package com.pnow.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DistrictController.class)
class DistrictControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("지역 목록 조회 유효성 검사")
    void getDistrictListTest() throws Exception {
        // given
        String id = "-1"; // 잘못된 cityId

        // when, then
        mvc.perform(get("/districts/city/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
