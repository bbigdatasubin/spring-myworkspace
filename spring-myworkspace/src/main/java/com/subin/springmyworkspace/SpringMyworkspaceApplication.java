package com.subin.springmyworkspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

// @내용: Annotation: 클래스나 필드, 메서드에 붙일 수 있음.
// 어노테이션이 붙어있는 코드를 특정 기능으로 자동화 하기 위한 기법

// @Scheduled 어노테이션으로 메서드를 실행하려면 필요
@EnableScheduling
@EnableCaching
@SpringBootApplication
public class SpringMyworkspaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMyworkspaceApplication.class, args);
	}

}
