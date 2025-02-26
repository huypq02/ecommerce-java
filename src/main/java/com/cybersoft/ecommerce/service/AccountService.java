package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.entity.UserEntity;
import com.cybersoft.ecommerce.entity.UserInfoEntity;
import com.cybersoft.ecommerce.request.AccountRequest;

public interface AccountService {
    public UserEntity getUserByAuthorToken();
    public boolean updateUser(AccountRequest accountRequest);
}
