package com.pnow.repository;

import com.pnow.dto.ReservationRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Slf4j
public class ReservationRepositoryTests {
    @Autowired
    private ReservationRepository reservationRepository;

    //findAll()
    @Test
    void 예약조회테스트(){
        //given
        ReservationRequestDTO dto=new ReservationRequestDTO();
        //dto.setStoreId(1);
        //dto.setSelectedDate();
        //dto.getSelectedTime();
        dto.setNumberOfPeople(2);


        //when

        //then

    }
}
