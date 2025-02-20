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
    private static final int ADMIN_ROLE_ID = 1;
    private static final int USER_ROLE_ID = 2;

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<?> registerAccount(@RequestBody RegisterRequest request) {

        registerService.register(request, USER_ROLE_ID);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("Register " + request.email()  + " successfully");
        baseResponse.setData(200);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest request) {

        registerService.register(request, ADMIN_ROLE_ID);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("Register " + request.email()  + " successfully");
        baseResponse.setData(200);
        return ResponseEntity.ok(baseResponse);
    }
}
