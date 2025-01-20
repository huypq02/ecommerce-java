package com.cybersoft.uniclub.service;

import com.cybersoft.uniclub.entity.UserEntity;
import com.cybersoft.uniclub.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import javax.crypto.SecretKey;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    public String login(String email, String password) {
        String token = "";

        Optional<UserEntity> user = userRepository.findByEmail(email);
        System.out.println(user);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
                token = Jwts.builder().setSubject(userEntity.getEmail()).signWith(key).compact();
                System.out.println(token);
            }

        }
        System.out.println(token);
        return token;
    }
}
