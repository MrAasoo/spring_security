package com.aasoo.jwt_auth_db_user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/")
    public String info() {
        return "JWT Auth DB User is running!";
    }

    @GetMapping("/test")
    public String test() {
        return "JWT Auth DB User authentication success!";
    }
}
