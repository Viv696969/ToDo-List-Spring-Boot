package com.vivekempire.TodoList.utils;

import java.time.Duration;
import java.util.*;
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
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        System.out.println(token);
        return token;


    }

    public Claims extractBody(String token){
         return  Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
    }

    public Map<String,Object> isValid(String token){
        token=token.split(" ")[1];
        Map<String, Object> data = new HashMap<>();
        try{
            Claims body = extractBody(token);
            if (body.getExpiration().after(new Date())) {
                String user_id=body.get("id", String.class);
                data.put("status", true);
                data.put("user_id", user_id);
                return  data;
            } else {
                data.put("status", false);
                return data;
            }
        }
        catch (Exception e){
            data.put("status",false);
            return data;
        }
    }



}
