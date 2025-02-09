package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.UserDto;
import com.cybersoft.ecommerce.entity.UserEntity;
import com.cybersoft.ecommerce.repository.AuthRepository;
import com.cybersoft.ecommerce.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public Map<String, Object> authenticateAndFetchProfile(String code, String loginType) {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        loginType = loginType.toLowerCase();
        String accessToken = "";
        String userInfoUri = "";
        Map<String, Object>  userInfo = null;
        switch (loginType) {
            case "facebook":
                String url = tokenUri
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
                url = "https://accounts.google.com/o/oauth2/v2/auth?client_id=YOUR_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI&scope=email&response_type=code";
                break;
            default:
        }
        return url;
    }

    @Override
    public String loginOrSignup(Map<String, Object> user) {
        // login or sign up by oauth_id
        String token = "";
        UserDto userDto = new UserDto();
        userDto.setEmail((String) user.get("email"));
        userDto.setOauthId((String) user.get("id"));

        Optional<UserEntity> existedUser = userRepository.findByOauthId(userDto.getOauthId());
        System.out.println(user); // TODO remove this line in production
        if (existedUser.isPresent()) {
            // Login
            UserEntity userEntity = existedUser.get();
            // Generate token
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
            token = Jwts.builder().setSubject(userEntity.getOauthId()).signWith(key).compact();
        } else {
            // Sign up
            UserEntity newUser = new UserEntity();
            // Set default role for new user
            newUser.setEmail(userDto.getEmail());
            newUser.setOauthId(userDto.getOauthId());
            userRepository.save(newUser);

            // Generate token
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
            token = Jwts.builder().setSubject(newUser.getOauthId()).signWith(key).compact();
        }

        System.out.println(token); // TODO remove this line in production

        return token;
    }
}
