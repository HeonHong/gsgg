package com.gsgg.gsggbe.characters.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gsgg.gsggbe.mapper.characters.CrtMainMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CrtMainService {

    private final CrtMainMapper crtMainMapper;

    /**
     * 2024-04-26
     * API를 통해 캐릭터 정보를 수신
     * commit test
     */
    @Transactional
    public void setAPICharacterInfos() {
        log.info("==================schedule start");

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
            // 한글 이름순 정렬 전 임시 List
            List<Map<String, Object>> tmpList = new ArrayList<>(data.values());

            //이름순 정렬 및 리스트 저장
            championList = tmpList.stream()
                                    .sorted(Comparator.comparing(crt -> crt.get("name").toString()))
                                    .toList();

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        //TB_CRT_BASIC 테이블 저장 데이터 세팅
        List<Map<String, Object>> crtBasicInfoList = this.setCrtBasicInfo(championList);
        //TB_CRT_BASIC 테이블 저장
        crtBasicInfoList.forEach(this.crtMainMapper::mergeCrtBasicInfo);

        //TB_CRT_DESC 테이블 저장 데이터 세팅
        List<Map<String, Object>> crtDescInfoList = this.setCrtDesc(championList);
        //TB_CRT_DESC 테이블 저장
        crtDescInfoList.forEach(this.crtMainMapper::mergeCrtDescInfo);

        //TB_CRT_IMG 테이블 저장 데이터 세팅
        List<Map<String, Object>> crtDescImgList = this.setCrtImg(championList);
        //TB_CRT_DESC 테이블 저장
        crtDescImgList.forEach(this.crtMainMapper::mergeCrtImg);


        log.info("==================schedule end");
    }

    /**
     * 2024-04-26
     * TB_CRT_BASIC 테이블 저장 데이터 세팅
     * API를 통해 받아온 데이터를 TB_CRT_BASIC 테이블 저장을 위해 가공
     *
     * @param championList List<Map<String, Object>>
     * @return List<Map<String, Object>>
     */
    private List<Map<String, Object>> setCrtBasicInfo(List<Map<String, Object>> championList) {

        List<Map<String, Object>> result = new ArrayList<>();

        championList.forEach(data -> {
            result.add(Map.of(
                    "crt_id",       data.get("key"),
                    "eng_name",     data.get("id"),
                    "kor_name",     data.get("name"),
                    "version_no",   data.get("version")
                    ));
        });

        return result;
    }


    /**
     * 2024-04-26
     * TB_CRT_DESC 테이블 저장 데이터 세팅
     * API를 통해 받아온 데이터를 TB_CRT_DESC 테이블 저장을 위해 가공
     *
     * @param championList List<Map<String, Object>>
     * @return List<Map<String, Object>>
     */
    private List<Map<String, Object>> setCrtDesc(List<Map<String, Object>> championList) {

        List<Map<String, Object>> result = new ArrayList<>();

        championList.forEach(data -> {
            result.add(Map.of(
                    "desc_id",      data.get("key"),
                    "crt_id",       data.get("key"),
                    "desc_content", data.get("blurb"),
                    "version_no",   data.get("version")
            ));
        });

        return result;
    }

    /**
     * 2024-04-27
     * TB_CRT_IMG 테이블 저장 데이터 세팅
     * API를 통해 받아온 데이터를 TB_CRT_IMG 테이블 저장을 위해 가공
     *
     * @param championList List<Map<String, Object>>
     * @return List<Map<String, Object>>
     */
    private List<Map<String, Object>> setCrtImg(List<Map<String, Object>> championList) {
        List<Map<String, Object>> result = new ArrayList<>();

        championList.forEach(data -> {
            result.add(Map.of(
                    "pic_id",       data.get("key"),
                    "crt_id",       data.get("key"),
                    "version_no",   data.get("version"),
                    "img_name",     ((Map)data.get("image")).get("full").toString()
            ));
        });

        return result;
    }

    /**
     * 2024-04-28
     * 캐릭터 정보 반환
     *
     * @return List<Map<String, Object>> champion info list
     */
    public List<Map<String, Object>> selectCrtInfos() {

        List<Map<String, Object>> result = new ArrayList<>();

        result = this.crtMainMapper.selectCrtInfos();

        return result;
    }
}
