package com.pnow.controller;

import com.pnow.dto.DistrictDTO;
import com.pnow.service.DistrictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class DistrictController {
    private final DistrictService districtService;
    private final HttpSession httpSession;

    @GetMapping("/district/{cityId}")

    public List<DistrictDTO> districtListRead(@PathVariable("cityId") Long cityId){
        log.info("district 메소드 진입. cityId: {}", cityId);

        //공용으로 쓸 데이터(cityId) 세션에 저장
        httpSession.setAttribute("cityId",cityId);

        // DistrictDTO 반환
        return districtService.findDistrictsWithCityId(cityId);
    }
}
