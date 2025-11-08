package com.pnow.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationScheduler {
    private final ReservationService reservationService;

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateReservationStatusScheduler() {
        log.info("스케줄러 실행 - 예약 상태 업데이트");
        reservationService.updateUserReservationStatus();
    }
}
