package com.pnow.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pnow.domain.City;
import com.pnow.domain.District;
import com.pnow.domain.Store;
import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.Reservation.ReservationStatus;
import com.pnow.domain.category.Category;
import com.pnow.domain.category.CategoryType;
import com.pnow.domain.user.Role;
import com.pnow.domain.user.User;
import com.pnow.dto.ReservationRequestDto;

@DataJpaTest
@Transactional
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    EntityManager em;
    
    @Test
    @DisplayName("가게의 선택날짜에 해당하는 예약된 시간 목록 조회")
    void findReservedTimes() { 
    	//given
    	Category category = Category.builder().categoryName(CategoryType.한식).build();
    	em.persist(category);

    	City city= City.builder().cityName("서울").build();
    	em.persist(city);

    	District district = District.builder().city(city).districtName("영등포구").build();
    	em.persist(district);

    	Store store=Store.builder()
    			.storeName("테스트 식당")
    			.category(category)
    			.district(district)
    			.detailAddress("456")
    			.openingTime(LocalTime.of(10,0))
    			.closingTime(LocalTime.of(22,0))
    			.phoneNumber("01012345678").build();
    	em.persist(store);

    	User user = User.builder()
    			.email("test@gmail.com")
    			.name("테스트")
    			.picture("테스트")
    			.role(Role.USER)
    			.build();
    	em.persist(user);

    	// 2026-03-20 12:00 예약
        Reservation reservation = Reservation.builder()
                .store(store)
                .user(user)
                .reservationDate(LocalDate.of(2026,3,20))
                .reservationTime(LocalTime.of(12,0))
                .reservationStatus(ReservationStatus.WAITING)
                .build();
        em.persist(reservation);

        em.flush();
        em.clear();
        
    	// when     
        List<LocalTime> result = reservationRepository.findReservedTimes(
                store.getId(),
                LocalDate.of(2026,3,20),
                ReservationStatus.WAITING
        );
        
        /* findByStoreIdAndReservationDateAndReservationStatus 테스트
         * 
        List<Reservation> result2 = reservationRepository.findByStoreIdAndReservationDateAndReservationStatus(
        		store.getId(),
                LocalDate.of(2026,3,20),
                ReservationStatus.WAITING
        		);
         */

        // then
        result.forEach(System.out::println);
        assertThat(result).contains(LocalTime.of(12,0));
        
        /*
        assertThat(result2)
        .extracting(Reservation::getReservationTime)
        .contains(LocalTime.of(12,0));
        */

    }

    //findAll()
    @Test
    void 예약조회테스트(){
        //given
        ReservationRequestDto dto=new ReservationRequestDto();
        //dto.setStoreId(1);
        //dto.setSelectedDate();
        //dto.getSelectedTime();
        dto.setNumberOfPeople(2);


        //when

        //then

    }
}
