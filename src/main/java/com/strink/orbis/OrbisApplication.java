package com.strink.orbis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class OrbisApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrbisApplication.class, args);
	}

}
