package com.gsgg.gsggbe.characters.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gsgg.gsggbe.mapper.characters.CrtMainMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class CrtMainService {

    private final CrtMainMapper crtMainMapper;

    public List<Map<String, Object>> selectCrtInfos() {

        List<Map<String, Object>> championList = new ArrayList<>();
        try {
            String urlstr = "https://ddragon.leagueoflegends.com/cdn/14.5.1/data/ko_KR/champion.json";
            URL rioturl = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) rioturl.openConnection();
            urlconnection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line;
            }

            Gson gson = new Gson();
            // JSON 전체 구조를 Map으로 파싱
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> fullData = gson.fromJson(result, type);

            // "data" 필드 내의 챔피언 데이터에 접근
            Type dataType = new TypeToken<Map<String, Map<String, Object>>>() {
            }.getType();
            Map<String, Map<String, Object>> data = gson.fromJson(gson.toJson(fullData.get("data")), dataType);

            // 챔피언 데이터를 리스트로 변환
            championList = new ArrayList<>(data.values());

            // 결과 확인
//            System.out.println(championList);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        List<Map<String, Object>> result = this.crtMainMapper.selectCrtInfos();
        log.info("result={}", result);

        return championList;
    }
}
