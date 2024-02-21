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

    @GetMapping("/district/list/{cityId}")
    public List<DistrictDTO> districtListRead(@PathVariable("cityId") Long cityId){
        log.info("/district/list/{cityId} get 메소드 진입. cityId = {}", cityId);
//
//        //선택된 데이터(cityId) 세션에 저장
//        httpSession.setAttribute("cityId",cityId);

        // DistrictDTO 반환
        return districtService.findDistrictsWithCityId(cityId);
    }
//    @GetMapping("/district/{districtId}")
//    public void districtIdSessionSet(@PathVariable("districtId") Long districtId){
//        log.info("districtId 세션에 저장 메소드 진입. districtId: {}", districtId);
//        //선택된 데이터(districtId) 세션에 저장
//        httpSession.setAttribute("districtId",districtId);
//    }
}
