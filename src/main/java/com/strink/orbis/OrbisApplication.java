package com.strink.orbis;

import com.strink.orbis.filter.RateLimitingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class OrbisApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrbisApplication.class, args);
	}

}
