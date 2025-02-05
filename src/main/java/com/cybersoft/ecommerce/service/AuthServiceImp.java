//package com.cybersoft.ecommerce.service;
//
//import com.cybersoft.ecommerce.repository.AuthRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//
//@Service
//public class AuthServiceImp implements AuthService {
//    @Autowired
//    private AuthRepository authRepository;
//
//    @Value("${spring.security.oauth2.client.registration.facebook.client-id}")
//    private String clientId;
//    @Value("${spring.security.oauth2.client.registration.facebook.client-secret}")
//    private String clientSecret;
//    @Value("${spring.security.oauth2.client.registration.facebook.redirect-uri}")
//    private String redirectUri;
//    @Value("${spring.security.oauth2.client.registration.facebook.auth-uri}")
//    private String authUri;
//    @Value("${spring.security.oauth2.client.registration.facebook.token-uri}")
//    private String tokenUri;
////    @Value("${spring.security.oauth2.client.registration.facebook.scope}")
////    private String scope;
//    @Value("${spring.security.oauth2.client.registration.facebook.user-info-uri}")
//    private String userInfoUri;
//
//    @Override
//    public Map<String, Object> authenticateAndFetchProfile(String code, String loginType) {
//        RestTemplate restTemplate = new RestTemplate();
////        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//        loginType = loginType.toLowerCase();
//        String accessToken = "";
//        String userInfo = "";
//        switch (loginType) {
//            case "facebook":
//                String url = tokenUri
//                        + "?client_id=" + clientId
//                        + "&redirect_uri=" + redirectUri
//                        + "&client_secret=" + clientSecret
//                        + "&code=" + code;
//                Map<String, String> response = restTemplate.getForObject(url, Map.class);
//                accessToken = response.get("access_token");
//                userInfo = userInfoUri + "&access_token=" + accessToken;
//                break;
//            case "google":
//                // Call Google API to get user profile
//                break;
//            default:
//        }
//        return restTemplate.getForObject(userInfo, Map.class);
//    }
//
//    @Override
//    public String generateAuthorizationUri(String loginType){
//        String url = "";
//        loginType = loginType.toLowerCase();
//        switch (loginType) {
//            case "facebook":
//                url = authUri + "?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&scope=email%20public_profile" + "&response_type=code" + "&loginType=" + loginType;
//                break;
//            case "google":
//                url = "https://accounts.google.com/o/oauth2/v2/auth?client_id=YOUR_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI&scope=email&response_type=code";
//                break;
//            default:
//        }
//        return url;
//    }
//}
