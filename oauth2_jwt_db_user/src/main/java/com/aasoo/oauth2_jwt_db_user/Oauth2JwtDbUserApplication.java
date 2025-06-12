package com.aasoo.oauth2_jwt_db_user;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Oauth2JwtDbUserApplication {

	static {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load(); // Load .env file
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
	}

	public static void main(String[] args) {
		SpringApplication.run(Oauth2JwtDbUserApplication.class, args);
	}

}
