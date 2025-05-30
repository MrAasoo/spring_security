package com.aasoo.basic_auth_db_user.init;

import com.aasoo.basic_auth_db_user.dao.request.AddUser;
import com.aasoo.basic_auth_db_user.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupUser implements CommandLineRunner {

    @Autowired
    private UserAuthService userAuthService;

    @Override
    public void run(String... args) throws Exception {
        userAuthService.addAdmin(new AddUser("admin", "admin123"), "ADMIN");
        userAuthService.addUser(new AddUser("user", "password"));
    }
}
