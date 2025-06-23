package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.UserDto;
import com.cybersoft.ecommerce.entity.UserEntity;
import com.cybersoft.ecommerce.request.UserRequest;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUser();
    boolean updateUser(UserRequest userRequest);
    void deleteUser(int id);
}
