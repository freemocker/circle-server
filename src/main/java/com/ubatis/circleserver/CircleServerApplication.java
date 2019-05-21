package com.ubatis.circleserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableCaching
public class CircleServerApplication {

	public static void main(String[] args) {
		System.setProperty("sun.jnu.encoding", "UTF-8");
		System.setProperty("user.timezone","Asia/Shanghai");
		SpringApplication.run(CircleServerApplication.class, args);
	}

}
