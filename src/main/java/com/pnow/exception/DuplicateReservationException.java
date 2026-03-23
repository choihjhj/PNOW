package com.pnow.exception;
/* 
 * 예약 추가 동시성 예외 처리
 * 
 * */
public class DuplicateReservationException extends RuntimeException{

	public DuplicateReservationException() {
        super("이미 예약된 시간입니다.");
    }

    public DuplicateReservationException(String message) {
        super(message);
    }
}
