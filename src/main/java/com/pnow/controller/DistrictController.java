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

    /*
     * 지역 조회
     * GET /district/list/{cityId}
     * return List<DistrictDTO>
     * */
    @GetMapping("/district/list/{cityId}")
    public List<DistrictDTO> districtListRead(@PathVariable("cityId") Long cityId){
        log.info("/district/list/{cityId} get 메소드 진입. cityId = {}", cityId);
        return districtService.findDistrictsWithCityId(cityId);
    }

}
