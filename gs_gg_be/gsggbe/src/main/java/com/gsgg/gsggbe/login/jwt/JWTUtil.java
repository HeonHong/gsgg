package com.gsgg.gsggbe.login.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    //객체 키
    private final SecretKey secretKey;

    //키 값 길이 주의
    //짧으면 에러남
    //32바이트 넘어가도 사용은 가능할 수 있으나 알고리즘 보안성과 호환성이 깨질 수 있다고 함.
    public JWTUtil(@Value("${JWT_PWD}")String secret){
        System.out.println("secret : "+secret);
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUserName(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }
    public String getRole(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);

    }

    public Boolean isExpired(String token){
        //현재 시간 기준으로 token의 만료여부 확인
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    //JWT 생성
    public String createJWT(String username,String role,Long expiredMs){
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
