package com.pnow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //스케줄러 ReservationScheduler 사용(예약상태 자동갱신)
public class PlacenowApplication {

	public static void main(String[] args) {
//		SpringApplication.run(PlacenowApplication.class, args);
		//설정한 값을 pid command 사용
		SpringApplication application = new SpringApplication(PlacenowApplication.class);
		application.addListeners(new ApplicationPidFileWriter());
		application.run(args);
	}

}
