package com.aasoo.basic_auth_db_user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/")
    public String info() {
        return "Basic Auth is running!";
    }

    @GetMapping("/test")
    public String test() {
        return "Basic Auth From DB authentication success!";
    }
}
