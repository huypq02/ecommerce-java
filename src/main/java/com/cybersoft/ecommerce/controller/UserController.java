package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.request.UserRequest;
import com.cybersoft.ecommerce.response.BaseResponse;
import com.cybersoft.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser() {
        BaseResponse response = new BaseResponse();
        response.setData(userService.getAllUser());
        response.setCode(200);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserRequest request, @PathVariable int id) {
        BaseResponse baseResponse = new BaseResponse();

        request.setId(id); // set id for request
        if (userService.updateUser(request)) {
            baseResponse.setMessage("Success");
        } else {
            baseResponse.setMessage("Error");
        }
        baseResponse.setCode(200);
        baseResponse.setData(request);

        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        BaseResponse baseResponse = new BaseResponse();
        userService.deleteUser(id);
        baseResponse.setCode(200);
        baseResponse.setData("Success");
        baseResponse.setMessage("Delete user successfully");
        return ResponseEntity.ok(baseResponse);
    }
}
