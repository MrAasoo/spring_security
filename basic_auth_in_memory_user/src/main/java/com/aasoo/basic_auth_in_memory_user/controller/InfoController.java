package com.aasoo.basic_auth_in_memory_user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/")
    public String info() {
        return "Basic Auth In Memory User is running!";
    }

    @GetMapping("/test")
    public String test() {
        return "Basic Auth In Memory User authentication success!";
    }
}
