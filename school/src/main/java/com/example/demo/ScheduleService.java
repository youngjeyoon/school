package com.example.demo;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

	@Scheduled(cron="0 0 0 * * *")
	public void schoolBackup() 
	{
		try
		{
			LocalDate today=LocalDate.now();
			
			String command = "mysqldump -u root -p1234 school --result-file=d:/school"+today.toString()+".sql";
 
			Process process = Runtime.getRuntime().exec(command);
	        int exitCode = process.waitFor();  // 0이면 정상종료 (명령어가 계속 실행될수있게 한다)
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		 
	}
}










