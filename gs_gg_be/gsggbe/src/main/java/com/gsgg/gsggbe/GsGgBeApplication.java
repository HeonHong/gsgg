package com.gsgg.gsggbe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.gsgg.gsggbe.mapper")//@MapperAnnotation 생략용도
@SpringBootApplication
public class GsGgBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GsGgBeApplication.class, args);
	}

}
