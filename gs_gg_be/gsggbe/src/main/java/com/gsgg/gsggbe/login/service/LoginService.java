package com.gsgg.gsggbe.login.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsgg.gsggbe.login.dto.KakaoToken;
import com.gsgg.gsggbe.login.dto.KakaoUserInfo;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class LoginService {

    //카카오 토큰 가져오기
    public KakaoToken getToken(String code) throws Exception {
        BufferedReader br = null;
        KakaoToken kakaoToken = new KakaoToken();

        try {
            URL url = new URL("https://kauth.kakao.com/oauth/token");
            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=utf-8");
            StringBuilder postData = new StringBuilder();
            postData.append("grant_type=authorization_code");
            postData.append("&client_id=862dd02fd929da2b9407d36d129a015d");
            postData.append("&redirect_uri=http://localhost:3000/loginpage");
            postData.append("&code="+code);

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            conn.getOutputStream().write(postDataBytes);

/*
            int resCode= conn.getResponseCode();
            System.out.println(resCode);*/

            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            ObjectMapper om = new ObjectMapper();
            kakaoToken = om.readValue(br, KakaoToken.class);

            System.out.println("kakao"+ kakaoToken);
        } catch (Exception e) {
            throw e;
        }  finally{
            if(br!=null){
                br.close();
            }
        }
        return kakaoToken;
    }

    //가져온 토큰으로 회원 정보 조회
    public void getMebrInfo(String accessToken) throws IOException {
        BufferedReader br = null;

        try {
            URL url = new URL("https://kapi.kakao.com/v2/user/me");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization","Bearer "+accessToken);
            conn.setRequestProperty("Content-type","Content-type: application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true);
            conn.getOutputStream();

            int resCode= conn.getResponseCode();
            System.out.println("resCode"+resCode);

            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            ObjectMapper om = new ObjectMapper();
            KakaoUserInfo userInfo = om.readValue(br, KakaoUserInfo.class);
            System.out.println("userInfo"+userInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(br!=null){
                br.close();
            }
        }
    }


}
