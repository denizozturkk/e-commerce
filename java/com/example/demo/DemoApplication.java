package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.mybatis.spring.annotation.MapperScan;


@RestController
@SpringBootApplication
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@MapperScan("com.example.demo.mappers")
public class DemoApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(DemoApplication.class, args);
	}


//	@GetMapping("/home")
//	public String v1(){
//		return "Welcome to home page";
//	}
//	@GetMapping("/site")
//	public String v2(){
//		return "You are in site";
//	}

}
