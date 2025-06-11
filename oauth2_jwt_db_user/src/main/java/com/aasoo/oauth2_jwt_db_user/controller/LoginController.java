package com.aasoo.oauth2_jwt_db_user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class LoginController {


    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String GITHUB_CLIENT_ID;

    private String REDIRECT_URI_GOOGLE = "http://localhost:8086/auth/google/callback";
    private String REDIRECT_URI_GITHUB = "http://localhost:8086/auth/github/callback";

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // maps to login.html in templates folder
    }

    @GetMapping("/login/google")
    public RedirectView googleLogin() {
        String redirectUri = UriComponentsBuilder.fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("client_id", GOOGLE_CLIENT_ID)
                .queryParam("redirect_uri", REDIRECT_URI_GOOGLE)
                .queryParam("response_type", "code")
                .queryParam("scope", "openid email profile")
                .queryParam("access_type", "offline")
                .build().toUriString();

        return new RedirectView(redirectUri);
    }

    @GetMapping("/login/github")
    public RedirectView githubLogin() {
        String githubAuthUrl = UriComponentsBuilder.fromUriString("https://github.com/login/oauth/authorize")
                .queryParam("client_id", GITHUB_CLIENT_ID)
                .queryParam("redirect_uri", REDIRECT_URI_GITHUB)
                .queryParam("scope", "read:user user:email")
                .queryParam("response_type", "code")
                .build()
                .toUriString();

        return new RedirectView(githubAuthUrl);
    }

}
