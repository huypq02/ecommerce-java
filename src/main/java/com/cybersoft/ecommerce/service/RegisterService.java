package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.entity.RoleEntity;
import com.cybersoft.ecommerce.entity.UserEntity;
import com.cybersoft.ecommerce.exception.InsertException;
import com.cybersoft.ecommerce.repository.RoleRepository;
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
    private RoleRepository roleUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request, String role) {
        try{
            String encodedPassword = passwordEncoder.encode(request.password());

            UserEntity user = new UserEntity();
            user.setEmail(request.email());
            user.setPassword(encodedPassword);
            user.setRole(roleUserRepository.findByRole(role).orElseThrow(() -> new InsertException("Role not found")));
            userRepository.save(user);
        } catch(Exception e) {
            throw new InsertException("Error while inserting user");
        }
    }
}
