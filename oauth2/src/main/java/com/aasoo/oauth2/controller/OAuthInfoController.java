package com.aasoo.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthInfoController {

    @GetMapping("/")
    public String home(){
        return "OAuth2 Service is running";
    }

    @GetMapping("/info")
    public String info() {
        return "This is the OAuth2 service providing authentication and authorization.";
    }
}
