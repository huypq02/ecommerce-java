package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.entity.UserEntity;
import com.cybersoft.ecommerce.entity.UserInfoEntity;
import com.cybersoft.ecommerce.request.AccountRequest;
import com.cybersoft.ecommerce.response.BaseResponse;
import com.cybersoft.ecommerce.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<?> getAccount() {
        BaseResponse baseResponse = new BaseResponse();

        UserEntity data = accountService.getUserByAuthorToken();
        baseResponse.setData(data);
        baseResponse.setCode(200);

        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping
    public ResponseEntity<?> updateAccount(AccountRequest accountRequest)
    {
        BaseResponse baseResponse = new BaseResponse();
        if (accountService.updateUser(accountRequest)) {
            baseResponse.setMessage("Success");
            UserEntity user = accountService.getUserByAuthorToken();
            baseResponse.setData(user);
        } else {
            baseResponse.setMessage("Error");
            baseResponse.setData("");
        }
        baseResponse.setCode(200);

        return ResponseEntity.ok(baseResponse);
    }
}
