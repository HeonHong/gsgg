package com.gsgg.gsggbe.login.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
//JWT구조 Header/Payload/Signature구조
//https://jwt.io/ 에서 Authorization Bearer 이후 값을 넣으면 값 확인 가능
public class JWTUtil {
    //객체 키
    private final SecretKey secretKey;

    //키 값 길이 주의
    //짧으면 에러남
    //32바이트 넘어가도 사용은 가능할 수 있으나 알고리즘 보안성과 호환성이 깨질 수 있다고 함.
    public JWTUtil(@Value("${JWT_PWD}")String secret){
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
        //JWT는 BASE64방식으로 인코딩하기 때문에 외부에서 쉽게 디코딩 됨
        //payload에 값을 싣어줄 때, 비밀번호 같은 중요한 정보는 절대!!! 싣어주면 안됨.
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))//생성일
                .expiration(new Date(System.currentTimeMillis()+expiredMs))//만료일
                .signWith(secretKey)
                .compact();
    }
}
