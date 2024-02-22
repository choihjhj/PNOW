package com.pnow.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

@SpringBootTest
@Slf4j
public class UserRepositoryTests {
    @Test
    public void test(){
        LocalTime now = LocalTime.of(11, 10, 00);
        log.info("now: {}", now);
        LocalTime open = LocalTime.of(11, 00, 00);
        log.info("open: {}", open);
        LocalTime close = LocalTime.of(23, 00, 00);
        log.info("close: {}", close);

        // 현재 시간이 오픈 시간보다 이후이고 종료 시간보다 전이면 영업중
        if (now.compareTo(open) >= 0 && now.compareTo(close) < 0) {
            log.info("영업중");
        } else {
            log.info("영업준비중");
        }
    }



    //@Autowired
    //private UserRepository userRepository;

    //save() 저장,업데이트
//    @Transactional
//    @Test
//    public void save() {
//    }
//
//    //findAll() 조회
//    @Test
//    public void findAll() {
//    }
//
//    //findById() id로 조회
//    @Test
//    public void findById() {
//    }
}
