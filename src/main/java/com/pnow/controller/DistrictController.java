package com.pnow.controller;

import com.pnow.dto.DistrictDTO;
import com.pnow.service.DistrictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("/districts")
@RequiredArgsConstructor
@RestController
@Slf4j
public class DistrictController {

    private final DistrictService districtService;

    /*
     * 지역 조회
     * GET /districts/list/{cityId}
     * return List<DistrictDTO>
     * */
    @GetMapping("/list/{cityId}")
    public List<DistrictDTO> getDistrictList(@PathVariable("cityId") Long cityId){
        log.info("지역 목록 조회 get 메소드 진입. cityId = {}", cityId);
        return districtService.findDistrictsWithCityId(cityId);
    }

}
