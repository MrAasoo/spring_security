package com.aasoo.oauth2_jwt_db_user.service.impl;

import com.aasoo.oauth2_jwt_db_user.dao.request.AuthRequest;
import com.aasoo.oauth2_jwt_db_user.dao.response.AuthResponse;
import com.aasoo.oauth2_jwt_db_user.service.AuthService;
import com.aasoo.oauth2_jwt_db_user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.google.authorization-grant-type}")
    private String AUTHORIZATION_GRANT_TYPE;

    @Value("${google.token-endpoint}")
    private String GOOGLE_TOKEN_ENDPOINT;

    @Value("${google.token-info-url}")
    private String GOOGLE_TOKEN_INFO_URL;

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String GITHUB_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String GITHUB_CLIENT_SECRET;

    @Value("${github.access-token}")
    private String GITHUB_ACCESS_TOKEN;

    @Value("${github.user}")
    private String GITHUB_USER;

    @Value("${github.user-emails}")
    private String GITHUB_USER_EMAILS;


    private final String REDIRECT_URI_GOOGLE = "http://localhost:8086/auth/google/callback";
    private final String REDIRECT_URI_GITHUB = "http://localhost:8086/auth/github/callback";


    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final RestTemplate restTemplate;
    private final UserService userService;

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.username(), authRequest.password());
        authManager.authenticate(authentication);

        return ResponseEntity.status(HttpStatus.OK).body(authToken(authRequest.username()));
    }


    public AuthResponse authToken(String username) {
        // This method can be used to refresh or validate the token
        UserDetails user = userDetailsService.loadUserByUsername(username);
        String token = jwtService.generateToken(user);
        return new AuthResponse(user.getUsername(), token);
    }

    @Override
    public ResponseEntity<?> googleAuth(String code) {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", GOOGLE_CLIENT_ID);
            params.add("client_secret", GOOGLE_CLIENT_SECRET);
            params.add("redirect_uri", REDIRECT_URI_GOOGLE);
            params.add("grant_type", AUTHORIZATION_GRANT_TYPE);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                    GOOGLE_TOKEN_ENDPOINT,
                    request,
                    Map.class
            );

            if (tokenResponse.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(tokenResponse.getStatusCode()).body("Failed to retrieve token from Google");
            }
            String idToken = (String) tokenResponse.getBody().get("id_token");

            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(
                    GOOGLE_TOKEN_INFO_URL + "?id_token=" + idToken,
                    Map.class
            );

            if (userInfoResponse.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(userInfoResponse.getStatusCode()).body("Failed to retrieve user info from Google");
            }

            Map<String, Object> userInfo = userInfoResponse.getBody();
            String username = (String) userInfo.get("email");
            if (username == null) {
                return ResponseEntity.badRequest().body("Email not found in Google user info");
            }

            UserDetails userDetails = null;
            try {
                userDetails = userDetailsService.loadUserByUsername(username);
            } catch (Exception e) {
                log.warn("User not found in database, creating new user: {}", username);
                userService.addUser(new AuthRequest(username, UUID.randomUUID().toString())); // Generate a random password
            }

            return ResponseEntity.status(HttpStatus.OK).body(authToken(username));

        } catch (Exception e) {
            log.error(AuthServiceImpl.class.getSimpleName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during Google login: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> githubAuth(String code) {
        try {
            // Step 1: Exchange code for access token
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.set("Accept", "application/json");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", GITHUB_CLIENT_ID);
            params.add("client_secret", GITHUB_CLIENT_SECRET);
            params.add("code", code);
            params.add("redirect_uri", REDIRECT_URI_GITHUB);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(GITHUB_ACCESS_TOKEN, request, Map.class);

            String accessToken = (String) tokenResponse.getBody().get("access_token");

            // Step 2: Fetch user info
            HttpHeaders userHeaders = new HttpHeaders();
            userHeaders.setBearerAuth(accessToken);
            userHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);
            ResponseEntity<Map> userResponse = restTemplate.exchange(GITHUB_USER, HttpMethod.GET, userRequest, Map.class);

            Map<String, Object> userData = userResponse.getBody();
            String username = (String) userData.get("email");

            // GitHub sometimes doesn't return email. You can fetch primary email via:
            if (username == null) {
                ResponseEntity<List> emailResp = restTemplate.exchange(GITHUB_USER_EMAILS, HttpMethod.GET, userRequest, List.class);
                List<Map<String, Object>> emails = emailResp.getBody();
                if (emails != null && !emails.isEmpty()) {
                    for (Map<String, Object> item : emails) {
                        if (Boolean.TRUE.equals(item.get("primary"))) {
                            username = (String) item.get("email");
                            break;
                        }
                    }
                }
            }

            if (username == null) return ResponseEntity.badRequest().body("Email not found from GitHub");

            // Step 3: Save or update user
            try {
                userDetailsService.loadUserByUsername(username);
            } catch (Exception e) {
                log.warn("User not found in database, creating new user: {}", username);
                userService.addUser(new AuthRequest(username, UUID.randomUUID().toString()));
            }

            String token = jwtService.generateToken(userDetailsService.loadUserByUsername(username));

            return ResponseEntity.status(HttpStatus.OK).body(authToken(username));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("GitHub Auth Error: " + e.getMessage());
        }
    }

}
