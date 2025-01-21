package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.request.RegisterRequest;
import com.cybersoft.ecommerce.response.BaseResponse;
import com.cybersoft.ecommerce.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<?> registerAccount(@RequestBody RegisterRequest request) {
        registerService.register(request);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("Register success");
        baseResponse.setData(200);
        return ResponseEntity.ok("Register success" + request.email());
    }
}
