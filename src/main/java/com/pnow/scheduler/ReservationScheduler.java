package com.pnow.scheduler;

import com.pnow.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component  // 스프링 빈 등록, 스케줄러 작동 필수
@RequiredArgsConstructor
@Slf4j
public class ReservationScheduler {
    private final ReservationService reservationService;

    /**
     * 1분마다 예약 상태 자동 업데이트
     * - 이전에 완료되지 않은 예약(WAITING)을 COMPLETE로 변경
     */
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateReservationStatus() {
        log.info("스케줄러 실행 - 예약 상태 업데이트 시작");
        try {
            reservationService.updateUserReservationStatus();
            log.info("스케줄러 실행 완료");
        } catch (Exception e) {
            log.error("스케줄러 실행 중 오류 발생", e);
        }
    }
}
