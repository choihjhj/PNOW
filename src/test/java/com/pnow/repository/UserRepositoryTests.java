package com.pnow.repository;

import com.pnow.domain.CategoryType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTests {
    @Test
    public void test(){
        System.out.println("카테고리 타입"+ CategoryType.values());
        System.out.println("카테고리[0]"+ CategoryType.values()[0]);
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
