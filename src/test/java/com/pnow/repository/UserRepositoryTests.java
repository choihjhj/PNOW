package com.pnow.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    //save() 저장,업데이트
    @Transactional
    @Test
    public void save() {
    }

    //findAll() 조회
    @Test
    public void findAll() {
    }

    //findById() id로 조회
    @Test
    public void findById() {
    }
}
