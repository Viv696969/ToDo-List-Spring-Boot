package com.vivekempire.TodoList.utils;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTHelper {

    @Value("${JWT_SECRET}")
    private String SECRET;
    private final long  expirationTime=Duration.ofDays(30).toMillis();

    public String createToken(String id,String email){
        Map<String,String> claims=new HashMap<>();
        claims.put("id",id);
        claims.put("email",email);
        String token= Jwts.builder()
                .setClaims(claims)                          // Set the claims
                .setIssuedAt(new Date(System.currentTimeMillis()))   // Set the issued time
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // Set the expiration time
                .signWith(SignatureAlgorithm.HS256, SECRET)  // Sign with the secret key
                .compact();
        System.out.println(token);
        return token;


    }


}
