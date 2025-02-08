package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.dto.UserDto;
import com.cybersoft.ecommerce.request.RegisterRequest;
import com.cybersoft.ecommerce.response.BaseResponse;
import com.cybersoft.ecommerce.service.AuthService;
import com.cybersoft.ecommerce.service.RegisterService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private RegisterService registerService;

    @GetMapping("/auth/social")
    public ResponseEntity<?> socialAuth(@RequestParam String loginType) {
        String data = authService.generateAuthorizationUri(loginType);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth/social/callback")
    public ResponseEntity<?> callback(@RequestParam String code, @RequestParam String loginType) {
        Map<String, Object> user = authService.authenticateAndFetchProfile(code, loginType);
        if (user == null) {
            BaseResponse response = new BaseResponse();
            response.setCode(400);
            response.setMessage("Authentication failed");
            return ResponseEntity.ok(response);
        }

        // Register or Login user if not exists
        String token = authService.loginOrSignup(user);
        BaseResponse response = new BaseResponse();
        response.setData(token);
        response.setCode(200);
        return ResponseEntity.ok(response);
    }
}
