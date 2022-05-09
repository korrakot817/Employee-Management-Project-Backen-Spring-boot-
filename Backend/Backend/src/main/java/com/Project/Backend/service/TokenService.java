package com.Project.Backend.service;

import com.Project.Backend.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Value("${app.token.secret}")
    private String secret;

    @Value("${app.token.issuer}")
    private String issuer;

    //สร้าง Token
    public String tokenize(User user) {

        //สร้างปฏิธิน
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60); // วันที่สร้าง token + 60 นาที
        Date expiresAt = calendar.getTime();

        //สร้างวันปัจุบัน
        Date now = new Date();


        return JWT.create()
                .withIssuer(issuer) //issuer = คนสร้าง token
                .withClaim("principal", user.getId())
                .withClaim("role", "USER")
                .withExpiresAt(expiresAt)
                .sign(algorithm());
    }

    // verify token
    public DecodedJWT verify(String token){
        try {
            JWTVerifier verifier = JWT.require(algorithm())
                    .withIssuer(issuer)
                    .build();

            return verifier.verify(token); // ถ้า verify ผ่านจะ get token ออกมาให้

        }catch (Exception e){ // ไม่ผ่าน get null
            return null;
        }

    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

}
