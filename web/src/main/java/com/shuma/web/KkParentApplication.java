package com.shuma.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author KingKong
 */
@SpringBootApplication(scanBasePackages = {"com.shuma"})
@MapperScan("com.shuma.dao")

public class KkParentApplication {

	public static void main(String[] args) {
		SpringApplication.run(KkParentApplication.class, args);
	}
}
