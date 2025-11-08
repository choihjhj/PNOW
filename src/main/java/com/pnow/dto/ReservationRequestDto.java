package com.pnow.dto;

import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.Reservation.ReservationStatus;
import com.pnow.domain.Store;
import com.pnow.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReservationRequestDto implements Serializable {
    @NotNull(message = "가게Id는 필수 값입니다.") //String은 @NotBlank, 나머지는 @NotNull
    private Long storeId;              //가게 Id

    @NotNull(message = "예약 날짜는 필수 값입니다.")
    private LocalDate selectedDate;    //예약 날짜

    @NotNull(message = "예약 시간은 필수 값입니다.")
    private LocalTime selectedTime;    //예약 시간

    @Min(1)
    private int numberOfPeople;        //인원수

    /**
     * DTO -> Entity 변환
     */
    public Reservation toEntity(User user, Store store){
        return Reservation.builder()
                .user(user)
                .store(store)
                .reservationDate(selectedDate)
                .reservationTime(selectedTime)
                .guestCount(numberOfPeople)
                .reservationStatus(ReservationStatus.WAITING) // 기본값 지정
                .build();
    }

}
