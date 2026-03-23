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

	//가게의 선택날짜에 예약된 reservationTime 목록 조회
	@Query(""
			+ "SELECT r.reservationTime "
			+ "FROM Reservation r "
			+ "WHERE r.store.id = :storeId "
			+ "AND r.reservationDate = :reservationDate "
			+ "AND r.reservationStatus = :reservationStatus "
			+ "")
	List<LocalTime> findReservedTimes(
			@Param("storeId") Long storeId,
			@Param("reservationDate") LocalDate reservationDate,
			@Param("reservationStatus") ReservationStatus reservationStatus
			);
	
    //storeId, reservationDate, reservationStatus에 해당하는 예약된 Reservation 목록 조회
    //List<Reservation> findByStoreIdAndReservationDateAndReservationStatus(Long storeId, LocalDate reservationDate, ReservationStatus reservationStatus);

    //user에 해당하는 예약목록 조회
    List<Reservation> findAllByUserAndReservationStatusOrderByReservationDateAscReservationTimeAsc(User user, ReservationStatus reservationStatus);

    //예약 상태 갱신 WAITING -> COMPLETE, ReservationScheduler에서 사용
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Reservation r SET r.reservationStatus = :newStatus " +
            "WHERE r.reservationStatus = :oldStatus " +
            "AND (r.reservationDate < :currentDate OR " +
            "(r.reservationDate = :currentDate AND r.reservationTime < :currentTime))")
    int bulkUpdateReservationStatus(@Param("oldStatus") ReservationStatus oldStatus,
                                    @Param("newStatus") ReservationStatus newStatus,
                                    @Param("currentDate") LocalDate currentDate,
                                    @Param("currentTime") LocalTime currentTime);

}
