package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.entity.UserEntity;
import com.cybersoft.ecommerce.exception.InsertException;
import com.cybersoft.ecommerce.repository.UserRepository;
import com.cybersoft.ecommerce.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request){
        try{
            String encodedPassword = passwordEncoder.encode(request.password());

            UserEntity user = new UserEntity();
            user.setEmail(request.email());
            user.setPassword(encodedPassword);
            user.setFullname(request.fullname());

            userRepository.save(user);
        } catch(Exception e) {
            throw new InsertException("Error while inserting user");
        }
    }
}
