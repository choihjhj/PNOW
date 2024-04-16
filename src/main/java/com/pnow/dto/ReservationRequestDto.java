package com.pnow.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReservationRequestDto {
    @NotNull(message = "가게Id는 필수 값입니다.") //String은 @NotBlank, 나머지는 @NotNull
    private Long storeId;              //가게 Id

    @NotNull(message = "예약 날짜는 필수 값입니다.")
    private LocalDate selectedDate;    //예약 날짜

    @NotNull(message = "예약 시간은 필수 값입니다.")
    private LocalTime selectedTime;    //예약 시간

    @Min(1)
    private int numberOfPeople;        //인원수

}
