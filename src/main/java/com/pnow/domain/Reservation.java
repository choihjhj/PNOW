package com.pnow.domain;

import com.pnow.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Setter //jpa 테스트를 위해
@Getter
@Entity
@DynamicInsert //@ColumnDefault 사용하려고
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private LocalDate reservationDate; // 예약 날짜

    @Column(nullable = false)
    private LocalTime reservationTime; // 예약 시간

    @ColumnDefault("1") // default 1
    @Column //(nullable = false)
    private int guestCount; // 인원 수

    @ColumnDefault("'WAITING'") // default 'WAITING'
    @Enumerated(EnumType.STRING)
    @Column //(nullable = false)
    private ReservationStatus reservationStatus; // 예약 상태:WAITING,COMPLETE 기본값은 'WAITING'
}
