package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.UserDto;
import com.cybersoft.ecommerce.entity.UserEntity;
import com.cybersoft.ecommerce.entity.UserInfoEntity;
import com.cybersoft.ecommerce.repository.AuthRepository;
import com.cybersoft.ecommerce.repository.RoleRepository;
import com.cybersoft.ecommerce.repository.UserInfoRepository;
import com.cybersoft.ecommerce.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Value("${jwt.secret}")
    private String secret;

    // Facebook OAuth2 configuration
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
    @Value("${spring.security.oauth2.client.registration.facebook.scope}")
    private String facebookScope;
    @Value("${spring.security.oauth2.client.registration.facebook.user-info-uri}")
    private String facebookUserInfoUri;
    @Value("${spring.security.oauth2.client.registration.facebook.response-type}")
    private String responseType;

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
        String url = "";
        String userInfoUri = "";
        Map<String, Object>  userInfo = null;
        switch (loginType) {
            case "facebook":
                url = tokenUri
                        + "?client_id=" + clientId
                        + "&redirect_uri=" + redirectUri
                        + "&client_secret=" + clientSecret
                        + "&code=" + code;
                Map<String, String> response = restTemplate.getForObject(url, Map.class);
                accessToken = response.get("access_token");
                userInfoUri = facebookUserInfoUri + "&access_token=" + accessToken;
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
                userInfoUri = googleUserInfoUri + "?access_token=" + accessToken;
                break;
            default:
        }

        userInfo = restTemplate.getForObject(userInfoUri, Map.class);
        return userInfo;
    }

    @Override
    public String generateAuthorizationUri(String loginType){
        String url = "";
        loginType = loginType.toLowerCase();
        switch (loginType) {
            case "facebook":
                url = authUri + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&scope=" + facebookScope + "&response_type=" + responseType + "&loginType=" + loginType;
                break;
            case "google":
                url = googleAuthUri + "?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUri + "&scope=email" + "&response_type=code" + "&loginType=" + loginType;
                break;
            default:
        }
        return url;
    }

    @Override
    public String loginOrSignup(Map<String, Object> user, String role) {
        // login or sign up by oauth_id
        String token = "";
        UserDto userDto = new UserDto();
        userDto.setEmail((String) user.get("email"));
        if (user.get("id") != null && !user.get("id").equals("")) {
            userDto.setOauthId((String) user.get("id"));
        } else {
            userDto.setOauthId((String) user.get("sub"));
        }

        Optional<UserEntity> existedUser = userRepository.findByOauthId(userDto.getOauthId());
        System.out.println(user); // TODO remove this line in production
        if (existedUser.isPresent()) {
            // Login
            UserEntity userEntity = existedUser.get();
            // Set issued at and expiration times
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.HOUR, 1); // Set expiration time to 1 hour from now
            Date expiration = calendar.getTime();
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
            token = Jwts.builder()
                    .claim("userId", userEntity.getId())
                    .claim("roleInfo", userEntity.getRole())
                    .claim("email", userEntity.getEmail())
                    .claim("oauthId", userEntity.getOauthId())
                    .issuedAt(now)
                    .expiration(expiration)
                    .signWith(key)
                    .compact();

        } else {
            // Sign up
            //User-info
            UserInfoEntity newUserInfo = userInfoRepository.save(new UserInfoEntity());

            UserEntity newUser = new UserEntity();
            // Set default role for new user
            newUser.setEmail(userDto.getEmail());
            newUser.setOauthId(userDto.getOauthId());
            newUser.setRole(roleRepository.findByRole(role).get());
            newUser.setUserInfo(newUserInfo);
            userRepository.save(newUser);

            // Set issued at and expiration times
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.HOUR, 1); // Set expiration time to 1 hour from now
            Date expiration = calendar.getTime();
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
            token = Jwts.builder()
                    .claim("userId", newUser.getId())
                    .claim("roleInfo", newUser.getRole())
                    .claim("email", newUser.getEmail())
                    .claim("oauthId", newUser.getOauthId())
                    .issuedAt(now)
                    .expiration(expiration)
                    .signWith(key)
                    .compact();
        }
        return token;
    }
}
