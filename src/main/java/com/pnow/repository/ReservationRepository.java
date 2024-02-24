package com.pnow.repository;

import com.pnow.domain.Reservation.Reservation;
import com.pnow.domain.Reservation.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStoreIdAndReservationDateAndReservationStatus(Long storeId, LocalDate reservationDate, ReservationStatus reservationStatus);
}
