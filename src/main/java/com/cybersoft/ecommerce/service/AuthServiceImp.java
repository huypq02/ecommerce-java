package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private AuthRepository authRepository;

    // Oauth2 Facebook
    @Value("${spring.security.oauth2.client.registration.facebook.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.facebook.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.facebook.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.facebook.auth-uri}")
    private String authUri;
    @Value("${spring.security.oauth2.client.registration.facebook.token-uri}")
    private String tokenUri;
//    @Value("${spring.security.oauth2.client.registration.facebook.scope}")
//    private String scope;
    @Value("${spring.security.oauth2.client.registration.facebook.user-info-uri}")
    private String userInfoUri;

    // Oauth2 Google
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;
    @Value("${spring.security.oauth2.client.registration.google.auth-uri}")
    private String googleAuthUri;
    @Value("${spring.security.oauth2.client.registration.google.token-uri}")
    private String googleTokenUri;
    @Value("${spring.security.oauth2.client.registration.google.user-info-uri}")
    private String googleUserInfoUri;

    @Override
    public Map<String, Object> authenticateAndFetchProfile(String code, String loginType) {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        loginType = loginType.toLowerCase();
        String accessToken = "";
        String userInfo = "";
        String url = "";
        switch (loginType) {
            case "facebook":
                url = tokenUri
                        + "?client_id=" + clientId
                        + "&redirect_uri=" + redirectUri
                        + "&client_secret=" + clientSecret
                        + "&code=" + code;
                Map<String, String> response = restTemplate.getForObject(url, Map.class);
                accessToken = response.get("access_token");
                userInfo = userInfoUri + "&access_token=" + accessToken;
                break;
            case "google":
                // Call Google API to get user profile
                Map<String, String> request = Map.of(
                        "client_id", googleClientId, "redirect_uri", googleRedirectUri,
                        "client_secret", googleClientSecret, "code", code,
                        "grant_type", "authorization_code"
                );
                ResponseEntity res = restTemplate.postForEntity(googleTokenUri, request, Map.class);

                String body = res.getBody().toString();
                accessToken = body.split(",")[0].replace("{access_token=", "");
                userInfo = googleUserInfoUri + "?access_token=" + accessToken;
                break;
            default:
        }
        return restTemplate.getForObject(userInfo, Map.class);
    }

    @Override
    public String generateAuthorizationUri(String loginType){
        String url = "";
        loginType = loginType.toLowerCase();
        switch (loginType) {
            case "facebook":
                url = authUri + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&scope=email%20public_profile" + "&response_type=code" + "&loginType=" + loginType;
                break;
            case "google":
                url = googleAuthUri + "?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUri + "&scope=email" + "&response_type=code" + "&loginType=" + loginType;
                break;
            default:
        }
        return url;
    }
}
