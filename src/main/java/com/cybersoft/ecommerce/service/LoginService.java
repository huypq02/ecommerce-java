package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.entity.UserEntity;
import com.cybersoft.ecommerce.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            // Set issued at and expiration times
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.HOUR, 1); // Set expiration time to 1 hour from now
            Date expiration = calendar.getTime();
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
                token = Jwts.builder()
                        .claim("userId", userEntity.getId())
                        .claim("roleInfo", userEntity.getRole())
                        .claim("email", userEntity.getEmail())
                        .issuedAt(now)
                        .expiration(expiration)
                        .signWith(key)
                        .compact();
            }
        }
        return token;
    }
}
