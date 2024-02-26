package com.pnow.repository;

import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.Reservation.ReservationStatus;
import com.pnow.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    //storeId, reservationDate, reservationStatus에 해당하는 예약목록 조회
    List<Reservation> findByStoreIdAndReservationDateAndReservationStatus(Long storeId, LocalDate reservationDate, ReservationStatus reservationStatus);

    //user에 해당하는 예약목록 조회
    List<Reservation> findAllByUserAndReservationStatusOrderByReservationDateAscReservationTimeAsc(User user, ReservationStatus reservationStatus);

    //예약 지난 목록 조회
//    @Query("SELECT r FROM Reservation r " +
//            "WHERE (r.reservationDate < :currentDate OR " +
//            "(r.reservationDate = :currentDate AND r.reservationTime <= :currentTime)) " +
//            "AND r.reservationStatus = 'WAITING'")
//    List<Reservation> findWaitingReservationsToUpdate(@Param("currentDate") LocalDate currentDate, @Param("currentTime") LocalTime currentTime);

    //예약 지난 목록 조회
    List<Reservation> findByReservationDateBeforeOrReservationDateAndReservationTimeBeforeAndReservationStatus(
            LocalDate currentDate, LocalDate currentDate2, LocalTime currentTime, ReservationStatus status);
}
