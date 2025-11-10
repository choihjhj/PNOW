package com.pnow.dto;

import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.Reservation.ReservationStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든생성자
public class ReservationDto implements Serializable {
    private Long id;                   //예약 id
    private Long storeId;              //가게 id
    private String storeName;          //가게 이름
    private LocalDate selectedDate;    //예약 날짜
    private LocalTime selectedTime;    //예약 시간
    private int numberOfPeople;        //인원수
    private ReservationStatus reservationStatus; //예약상태
    private LocalDateTime createdDate; //예약접수일

    public static ReservationDto fromEntity(Reservation reservation){
        return ReservationDto.builder()
                .id(reservation.getId())
                .storeId(reservation.getStore().getId())
                .storeName(reservation.getStore().getStoreName())
                .selectedDate(reservation.getReservationDate())
                .selectedTime(reservation.getReservationTime())
                .numberOfPeople(reservation.getGuestCount())
                .reservationStatus(reservation.getReservationStatus())
                .createdDate(reservation.getCreatedDate())
                .build();
    }

    private String formatTime(LocalDateTime time) { return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm")); }
}
