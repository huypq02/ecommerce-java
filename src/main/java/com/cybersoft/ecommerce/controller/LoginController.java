package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.entity.UserEntity;
import com.cybersoft.ecommerce.response.BaseResponse;
import com.cybersoft.ecommerce.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        String token = loginService.login(user.getEmail(), user.getPassword());
        System.out.println(token);
        BaseResponse response = new BaseResponse();
        response.setData(token);
        response.setCode(200);
        return ResponseEntity.ok(response);
    }
}
