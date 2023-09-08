package com.baseproject.tokenbasedauthentication;

import com.baseproject.tokenbasedauthentication.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TokenbasedauthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokenbasedauthenticationApplication.class, args);
	}

}
