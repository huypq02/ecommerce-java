package com.cybersoft.ecommerce.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Map;

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
        String data = null;

        try {
            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            Map<String, Object> roleInfo = (Map<String, Object>) claims.get("roleInfo");
            System.out.println(roleInfo.get("role"));
            data = roleInfo.get("role").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public Claims getClaims(String token){
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        Claims claims = null;

        try {
            claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Token is invalid");
        }
        return claims;
    }
}
