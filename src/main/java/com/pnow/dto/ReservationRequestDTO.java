package com.pnow.dto;

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
public class ReservationRequestDTO {
    private Long storeId;              //가게 Id
    private LocalDate selectedDate;    //예약 날짜
    private LocalTime selectedTime;    //예약 시간
    private int numberOfPeople;        //인원수
}
