package com.pnow.service;

import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pnow.dto.ReservationRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
	private final JavaMailSender mailSender;

	@Async //비동기 처리
    public void sendReservationConfirm(String email, ReservationRequestDto requestDTO, String status) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[PNOW] " + requestDTO.getStoreName() + " 예약이 " + status + "되었습니다."); //제목
        message.setText(
                "예약 안내\n\n" +
                "매장명 : " + requestDTO.getStoreName() + "\n" +
                "예약 날짜 : " + requestDTO.getSelectedDate() + "\n" +
                "예약 시간 : " + requestDTO.getSelectedTime() + "\n" +
                "예약 인원 : " + requestDTO.getNumberOfPeople() + "명\n\n" +
                "예약이 " + status + "되었습니다.\n" +
                "이용해주셔서 감사합니다."
        );

        try {
        	mailSender.send(message);
        	log.info("메일 발송 성공!");
        } catch(Exception e) {
        	log.error("메일 발송 실패!", e);
        	throw new MailSendException("메일 발송 중 오류가 발생했습니다.", e);

        }
    }

}
