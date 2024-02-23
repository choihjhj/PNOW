package com.pnow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든생성자
public class ReservationRequestDTO {
    private Long storeId;           //가게 id
    private String selectedDate;    //예약 날짜
    private String selectedTime;    //예약 시간
    private int numberOfPeople;     //인원수
}
