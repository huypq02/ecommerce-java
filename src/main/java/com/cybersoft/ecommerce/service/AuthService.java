package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.UserDto;
import org.apache.catalina.User;

import java.util.Map;

public interface AuthService {
    String generateAuthorizationUri(String loginType);
    Map<String, Object> authenticateAndFetchProfile(String code, String loginType);
    String loginOrSignup(Map<String, Object> user);
}
