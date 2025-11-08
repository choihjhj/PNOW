package com.pnow.repository;

import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.Reservation.ReservationStatus;
import com.pnow.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    //예약 COMPLETE로 처리안 된 WAITING 목록 조회
    List<Reservation> findByReservationDateBeforeOrReservationDateAndReservationTimeBeforeAndReservationStatus(
            LocalDate currentDate, LocalDate currentDate2, LocalTime currentTime, ReservationStatus status);

    @Modifying
    @Query("UPDATE Reservation r SET r.reservationStatus = :newStatus " +
            "WHERE r.reservationStatus = :oldStatus " +
            "AND (r.reservationDate < :currentDate OR " +
            "(r.reservationDate = :currentDate AND r.reservationTime < :currentTime))")
    int bulkUpdateReservationStatus(@Param("oldStatus") ReservationStatus oldStatus,
                                    @Param("newStatus") ReservationStatus newStatus,
                                    @Param("currentDate") LocalDate currentDate,
                                    @Param("currentTime") LocalTime currentTime);

}
