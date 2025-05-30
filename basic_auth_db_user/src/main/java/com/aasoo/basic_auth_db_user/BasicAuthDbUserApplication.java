package com.aasoo.basic_auth_db_user;

import com.aasoo.basic_auth_db_user.dao.request.AddUser;
import com.aasoo.basic_auth_db_user.service.UserAuthService;
import com.aasoo.basic_auth_db_user.service.impl.UserDetailsServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BasicAuthDbUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicAuthDbUserApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }
}
