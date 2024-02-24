package com.pnow.dto;

import com.pnow.domain.Reservation.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든생성자
public class ReservationDetailDTO {
    private Long id;                   //예약 id
    private String storeName;          //가게 이름
    private LocalDate selectedDate;    //예약 날짜
    private LocalTime selectedTime;    //예약 시간
    private int numberOfPeople;        //인원수
    private ReservationStatus reservationStatus; //예약상태
}
