package com.example.app.scheduled;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling

public class Scheduling {

//	@Scheduled(fixedRate = 1000)
//	public void t1() {
//		System.out.println("Scheduling's t1 invoke");
//	}
	
//	@Scheduled(cron = "* * * * * *") // 초 분 시 일 월 요일
//	public void t2() {
//		System.out.println("Scheduling's t2 invoke");
//	}

}
