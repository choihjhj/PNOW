package com.pnow.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.Rollback;
import com.pnow.config.auth.dto.CustomUserPrincipal;
import com.pnow.domain.user.Role;
import com.pnow.domain.user.User;
import com.pnow.dto.ReservationRequestDto;
import com.pnow.service.ReservationService;

@SpringBootTest
@Transactional      // 테스트 트랜잭션
@Rollback           // 끝나면 자동 롤백
class ReservationControllerTest {

	@Autowired
    ReservationService reservationService;
    
  
    @Test
    @DisplayName("예약 추가 동시성 테스트")
    void 동시에_100개_예약하면_1개만_성공_나머지_99개_예외처리() throws InterruptedException {

        int threadCount = 100; //100개 동시 예약

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount); //100개 스레드풀 생성
        CountDownLatch latch = new CountDownLatch(threadCount); //카운트다운으로 100~0개의 작업이 끝날 때까지 기다리는 카운터
        AtomicInteger successCount = new AtomicInteger(0); //예약성공 스레드 갯수 변수

        ReservationRequestDto requestDto =
                ReservationRequestDto.builder()
                        .storeId(53L)
                        .storeName("테스트가게")
                        .selectedDate(LocalDate.now().plusDays(1))
                        .selectedTime(LocalTime.of(18,0))
                        .numberOfPeople(2)
                        .build();

        // 테스트용 로그인 사용자
        User loginUser = new User("홍길동", "test@example.com", "pic.png", Role.USER);
        loginUser.setId(3L); 

        CustomUserPrincipal principal = new CustomUserPrincipal();
        principal.setId(loginUser.getId());
        principal.setName(loginUser.getName());
        principal.setEmail(loginUser.getEmail());
        principal.setPicture(loginUser.getPicture());
        principal.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")));


        // 스레드별 예약 시도
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    reservationService.makeReservation(requestDto, principal);
                    successCount.incrementAndGet(); 
                } catch (Exception e) {
                    System.out.println("예약 실패: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                } finally {
                    latch.countDown(); //해당 스레드 작업 완료처리
                }
            });
        }

        latch.await(); //모든 작업 끝날때까지 기다림
        executorService.shutdown();

        System.out.println("성공한 예약 수 = " + successCount.get());

        // 동시성 검증
        assertThat(successCount.get()).isEqualTo(1);
    }
    
   

}
