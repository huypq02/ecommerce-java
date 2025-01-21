package com.cybersoft.ecommerce.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtHelper {
    @Value("${jwt.secret}")
    private String secret;

    public boolean decryptToken(String token) {
        boolean result = false;

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
//        SecretKey key = Jwts.SIG.HS256.key().build();
//        String jws = Jwts.builder().subject("Joe").signWith(key).compact();

        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getDataToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        String role = null;

        try {
            role = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }
}
