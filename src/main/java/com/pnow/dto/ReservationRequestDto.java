package com.pnow.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.pnow.domain.Store;
import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.Reservation.ReservationStatus;
import com.pnow.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor //기본생성자, JUnit test에서 @Builder사용하려고
public class ReservationRequestDto implements Serializable {
    @NotNull(message = "가게Id는 필수 값입니다.") //String은 @NotBlank, 나머지는 @NotNull
    private Long storeId;              //가게 Id
    
    @NotBlank(message = "가게이름은 필수 값입니다.")
    private String storeName;

    @NotNull(message = "예약 날짜는 필수 값입니다.")
    private LocalDate selectedDate;    //예약 날짜

    @NotNull(message = "예약 시간은 필수 값입니다.")
    private LocalTime selectedTime;    //예약 시간

    @Min(1)
    private int numberOfPeople;        //인원수
    
    @Builder
    public ReservationRequestDto(Long storeId, String storeName, LocalDate selectedDate, LocalTime selectedTime, int numberOfPeople){
    	this.storeId=storeId;
    	this.storeName=storeName;
    	this.selectedDate=selectedDate;
    	this.selectedTime=selectedTime;
    	this.numberOfPeople=numberOfPeople;
    }
    
    @Builder
    public ReservationRequestDto(String storeName, LocalDate selectedDate, LocalTime selectedTime, int numberOfPeople){
    	this.storeName=storeName;
    	this.selectedDate=selectedDate;
    	this.selectedTime=selectedTime;
    	this.numberOfPeople=numberOfPeople;
    }
    
    @Builder
    public ReservationRequestDto(LocalDate selectedDate, LocalTime selectedTime, int numberOfPeople){
    	this.selectedDate=selectedDate;
    	this.selectedTime=selectedTime;
    	this.numberOfPeople=numberOfPeople;
    }

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
    
    // Reservation Entity → ReservationRequestDto 변환
    public static ReservationRequestDto fromReservationEntity(Reservation reservation) {
        return ReservationRequestDto.builder()
        		.selectedDate(reservation.getReservationDate())
        		.selectedTime(reservation.getReservationTime())
        		.numberOfPeople(reservation.getGuestCount())
				.build();
    }

}
