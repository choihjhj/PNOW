package com.pnow.controller;

import com.pnow.dto.StoreDTO;
import com.pnow.service.ReservationService;
import com.pnow.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;
    private final StoreService storeService;

    /*
     * reservation.html 예약 페이지 접속
     * - 가게 조회 후 model에 저장
     * GET /reservation/{storeId}
     * return "/reservation/reservation"
     * */
    @GetMapping("/{storeId}")
    public String reservation( @PathVariable("storeId") Long storeId, Model model){
        log.info("/reservation/{storeId} get 메소드 진입 storeId = {}",storeId);

        StoreDTO storeDTO = storeService.findStoreDTO(storeId);
        model.addAttribute("store", storeDTO);

        return "reservation/reservation";
    }

    /*
     *
     * 예약 가능 시간 조회
     * GET /reservation/{storeId}/{reservationDate}
     * return List<ReservationAbleTimeDTO>
     * */
    @GetMapping("/{storeId}/{reservationDate}")
    @ResponseBody
    public void reservationTimeRead(@PathVariable("storeId") Long storeId,
                                                            @PathVariable("reservationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate reservationDate){
        log.info("/reservation/{storeId}/{reservationDate} get 메소드 진입 storeId = {}, reservationDate = {}",storeId,reservationDate);
        /*List<ReservationAbleTimeDTO>*/

    }
}
