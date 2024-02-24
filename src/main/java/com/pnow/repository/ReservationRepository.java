package com.pnow.repository;

import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.Reservation.ReservationStatus;
import com.pnow.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    //storeId, reservationDate, reservationStatus에 해당하는 예약목록 조회
    List<Reservation> findByStoreIdAndReservationDateAndReservationStatus(Long storeId, LocalDate reservationDate, ReservationStatus reservationStatus);

    //user에 해당하는 예약목록 조회
    List<Reservation> findAllByUserAndReservationStatusOrderByCreatedDateDesc(User user, ReservationStatus reservationStatus);
}
