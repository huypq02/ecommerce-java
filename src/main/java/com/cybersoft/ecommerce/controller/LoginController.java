package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.response.BaseResponse;
import com.cybersoft.ecommerce.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password ) {
        String token = loginService.login(email, password);
        System.out.println(token);
        BaseResponse response = new BaseResponse();
        response.setData(token);
        response.setCode(200);
        return ResponseEntity.ok(response);
    }


}
