package com.pnow.domain.Reservation;

import com.pnow.domain.BaseTimeEntity;
import com.pnow.domain.Store;
import com.pnow.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@DynamicInsert //@ColumnDefault 사용하려고
@NoArgsConstructor //기본생성자, JUnit test에서 @Builder사용하려고

@Table(
		indexes = {
				@Index(
						name = "idx_reservation_store_date_status",
						columnList = "store_id, reservationDate, reservationStatus"
						)
		},
		uniqueConstraints = {
				@UniqueConstraint(
						name = "uk_store_date_time_status",
						columnNames = {"store_id", "reservationDate", "reservationTime", "reservationStatus"}
						)
		}
		)
public class Reservation extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reservation_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@Column(nullable = false)
	private LocalDate reservationDate; // 예약 날짜

	@Column(nullable = false)
	private LocalTime reservationTime; // 예약 시간

	@ColumnDefault("1") // default 1
	@Column //(nullable = false)
	private int guestCount; // 인원 수

	@ColumnDefault("'WAITING'") // default 'WAITING'
	@Enumerated(EnumType.STRING)
	@Column //(nullable = false)
	private ReservationStatus reservationStatus; // 예약 상태:WAITING,COMPLETE 기본값은 'WAITING'

	@Builder
	public Reservation(User user, Store store, LocalDate reservationDate,
			LocalTime reservationTime, int guestCount, ReservationStatus reservationStatus){
		this.user = user;
		this.store = store;
		this.reservationDate = reservationDate;
		this.reservationTime = reservationTime;
		this.guestCount = guestCount;
		this.reservationStatus = reservationStatus;
	}

}
