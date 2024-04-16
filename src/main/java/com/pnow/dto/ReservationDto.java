package com.pnow.dto;

import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.Reservation.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor  //기본생성자
@AllArgsConstructor //모든생성자
public class ReservationDto {
    private Long id;                   //예약 id
    private String storeName;          //가게 이름
    private LocalDate selectedDate;    //예약 날짜
    private LocalTime selectedTime;    //예약 시간
    private int numberOfPeople;        //인원수
    private ReservationStatus reservationStatus; //예약상태
    private String createdDate; //예약접수일

    public ReservationDto(Reservation reservation){
        this.id=reservation.getId();
        this.storeName=reservation.getStore().getStoreName();
        this.selectedDate=reservation.getReservationDate();
        this.selectedTime=reservation.getReservationTime();
        this.numberOfPeople=reservation.getGuestCount();
        this.reservationStatus=reservation.getReservationStatus();
        this.createdDate=formatTime(reservation.getCreatedDate());
    }
    private String formatTime(LocalDateTime time) { return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm")); }
}
