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
    private static final String ADMIN_ROLE = "ROLE_ADMIN";
    private static final String STAFF_ROLE = "ROLE_STAFF";
    private static final String USER_ROLE = "ROLE_USER";

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<?> registerAccount(@RequestBody RegisterRequest request) {

        registerService.register(request, USER_ROLE);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("Register " + request.email()  + " successfully");
        baseResponse.setData(200);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest request) {

        registerService.register(request, ADMIN_ROLE);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("Register " + request.email()  + " successfully");
        baseResponse.setData(200);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/staff")
    public ResponseEntity<?> registerStaff(@RequestBody RegisterRequest request) {
        String role = request.role();
        registerService.register(request, role);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("Register " + request.email()  + " successfully");
        baseResponse.setData(200);
        return ResponseEntity.ok(baseResponse);
    }
}
