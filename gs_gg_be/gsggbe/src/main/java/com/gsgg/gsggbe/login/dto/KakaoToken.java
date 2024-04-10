package com.gsgg.gsggbe.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class KakaoToken {

    //토큰 타입, bearer로 고정
    @JsonProperty("token_type")
    private String tokenType;

    //사용자 액세스 토큰 값
    @JsonProperty("access_token")
    private String accessToken;

    //ID 토큰 값(OpenID Connect)
    @JsonProperty("id_token")
    private String idToken;

    //액세스 토큰과 ID 토큰의 만료 시간(초)
    @JsonProperty("expires_in")
    private int expiresIn;

    //사용자 리프레시 토큰 값
    @JsonProperty("refresh_token")
    String refreshToken;

    //리프레시 토큰 만료 시간(초)
    @JsonProperty("refresh_token_expires_in")
    int refreshTokenExpiresIn;

    //인증된 사용자의 정보 조회 권한 범위.범위가 여러 개일 경우, 공백으로 구분(OpenID Connect)
    @JsonProperty("scope")
    private String scope;


}