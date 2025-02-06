package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.response.BaseResponse;
import com.cybersoft.ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class OauthGoogleController {
    @Autowired
    private AuthService authService;

    @GetMapping("/auth/login")
    public ResponseEntity<BaseResponse> login(@RequestParam String loginType) {
        BaseResponse baseResponse = new BaseResponse();
            String url = authService.generateAuthorizationUri(loginType);
            baseResponse.setData(url);
            baseResponse.setCode(200);
            return ResponseEntity.ok(baseResponse);

    }
    @GetMapping("/auth/google/callback")
    public ResponseEntity<?> googleCallback(@RequestParam String code) {
        Map<String, Object> user = authService.authenticateAndFetchProfile(code, "google");
        BaseResponse baseResponse = new BaseResponse();
        if (user == null) {

            baseResponse.setCode(400);
            baseResponse.setMessage("Login failed");
            baseResponse.setData(null);
            return ResponseEntity.ok(baseResponse);
        }
        baseResponse.setCode(200);
        baseResponse.setMessage("Login successful");
        baseResponse.setData(user);
        return ResponseEntity.ok(baseResponse);
    }

}
