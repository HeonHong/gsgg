package com.gsgg.gsggbe.login.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsgg.gsggbe.login.dto.KakaoToken;
import com.gsgg.gsggbe.login.dto.KakaoUserInfo;
import com.gsgg.gsggbe.mapper.logIn.LogInMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

@Slf4j
@Service
public class LoginService {

    @Value("${CLIENT_ID}")
    private String clientId;

    @Autowired
    private LogInMapper logInMapper;

    //카카오 액세스 토큰 가져오기
    public String getToken(String code) {
        KakaoToken kakaoToken = new KakaoToken();
        String accessToken = null;

        //요청 시 필요한 파라미터 문자 > 바이트 데이터 처리
        StringBuilder postData = new StringBuilder();
        postData.append("grant_type=authorization_code");
        postData.append("&client_id=" + clientId);
        postData.append("&redirect_uri=http://localhost:3000/login");
        postData.append("&code=" + code);
        byte[] postDataBytes = new byte[0];
        try {
            postDataBytes = postData.toString().getBytes("UTF-8");


            URL url = new URL("https://kauth.kakao.com/oauth/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.getOutputStream().write(postDataBytes);

            int resCode = conn.getResponseCode();

            if (resCode == 200) {
//              데이터 클 경우 점진적 데이터 처리용
//              아래에서 InputStreamReader로 바이트 데이터를 문자로 처리해서 받아옴
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

//                    인스턴스 변경 필요할 경우 ObjectMapper 사용
                ObjectMapper om = new ObjectMapper();
                kakaoToken = om.readValue(br, KakaoToken.class);


//                    accessToken = String.valueOf(tokenJ.get("access_token"));

//              finally에 두지 말아야 함.
//              처음에 finally에 뒀는데 BufferedReader 생성 중 예외 발생하면 애초에 생성이 안되서 닫을 게 없어짐.
                br.close();
            } else {
                String responseMessage = conn.getResponseMessage();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String errorResult = br.readLine();
                log.error("rescode {}, responseMessage {}, errorResult {}", resCode,responseMessage, errorResult);
                br.close();
            }

        } catch (IOException e) {
            log.error("카카오 토큰 받기 실패");
        }

        return kakaoToken.getAccessToken();
    }

    //가져온 토큰으로 회원 정보 조회
    public KakaoUserInfo getKakaoUserInfo(String accessToken) {
        KakaoUserInfo userInfo = null;
        try {
            URL url = new URL("https://kapi.kakao.com/v2/user/me");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Content-type", "Content-type: application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true);
            conn.getOutputStream();

            int resCode = conn.getResponseCode();
            if (resCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                if (br.ready()) {
                    ObjectMapper om = new ObjectMapper();
                    userInfo = om.readValue(br, KakaoUserInfo.class);
                }
                br.close();

            } else {
                log.error("카카오 고객정보 조회 네트워크 오류");
            }

        } catch (IOException e) {
            log.info("카카오 고객정보 조회 실패");
        }
        return userInfo;
    }


    public String getUserName(String id) {
        //카카오 아이디가 존재 시, 원래 아이디 가져오기
        String username = logInMapper.getUsername(id);
        if (username==null)  return null;
        else return username;
    }
}
