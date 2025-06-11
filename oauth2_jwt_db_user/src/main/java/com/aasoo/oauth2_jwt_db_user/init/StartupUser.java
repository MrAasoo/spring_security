package com.aasoo.oauth2_jwt_db_user.init;

import com.aasoo.oauth2_jwt_db_user.dao.request.AuthRequest;
import com.aasoo.oauth2_jwt_db_user.service.RoleService;
import com.aasoo.oauth2_jwt_db_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupUser implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        roleService.saveRole("OWNER", "ADMIN", "USER", "GUEST");
        userService.addUser(new AuthRequest("admin", "admin123"), "ADMIN");
        userService.addUser(new AuthRequest("user", "password"));
    }
}
