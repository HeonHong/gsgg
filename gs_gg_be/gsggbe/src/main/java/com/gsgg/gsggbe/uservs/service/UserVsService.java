package com.gsgg.gsggbe.uservs.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserVsService {
    // 종욱님 key
    private final String apiKey = "RGAPI-3c872789-bc9e-4668-b5f1-ad8d49383207";

    // WebClient의 인스턴스 생성
    private final WebClient client = WebClient.create();

    public Mono<Map<String, String>> getUserPuuid(String mySummonerName, String yourSummonerName, String region) {
        try {
            String myRequestURL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + mySummonerName + "/" + region +"?api_key=" + apiKey;
            String yourRequestURL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + yourSummonerName + "/" + region + "?api_key=" + apiKey;

            Mono<String> myPuuidMono = client.get()
                    .uri(myRequestURL)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(this::parsePuuid)
                    .doOnNext(result -> System.out.println("My Response: " + result))
                    .doOnError(error -> System.err.println("My Error: " + error));

            Mono<String> yourPuuidMono = client.get()
                    .uri(yourRequestURL)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(this::parsePuuid)
                    .doOnNext(result -> System.out.println("Your Response: " + result))
                    .doOnError(error -> System.err.println("Your Error: " + error));

            return Mono.zip(myPuuidMono, yourPuuidMono)
                    .map(tuple -> {
                        Map<String, String> result = new HashMap<>();
                        result.put("myPuuid", tuple.getT1());
                        result.put("yourPuuid", tuple.getT2());
                        return result;
                    });
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
    // 가져온 res에서 puuid만 꺼내기
    private String parsePuuid(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        // https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java
        try {
            // response  JSON 데이터를 문자열로 가져옴
            System.out.println(" parsePuuid res 확인" + response);
            // {"puuid":"TI4gn95v2Jj5BoNALwkQFa9-2uzNwLrepdpQi5KaN4QGgCvvTv59tpiEC4L1mxam5ugoeX6nrEq2LA","gameName":"빵준갓","tagLine":"KR1"}
            JsonNode root = objectMapper.readTree(response); // JSON 문자열을 JsonNode로 파싱
            System.out.println(" root ");
            return root.get("puuid").asText();
        } catch (Exception e) {
            System.err.println("parsePuuid e " + e.getMessage());
            return null;
        }
    }

    public Mono<String> getUserId(String puuid) {
        try {
            String requestURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/" + puuid
                    +"?api_key=" + apiKey;

            return client.get()
                    .uri(requestURL)
                    .retrieve()
                    .bodyToMono(String.class);
        } catch ( Exception e ) {
            return Mono.error(e);
        }
    }
    
    public Mono<String> getSummonerInfo(String id) {
        try {
            String requestURL = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner//" + id
                    +"?api_key=" + apiKey;
            return client.get()
                    .uri(requestURL)
                    .retrieve()
                    .bodyToMono(String.class);
        } catch ( Exception e ) {
            return Mono.error(e);
        }
    }

    public Mono<Map<String, List<String>>> getMatchIds(String myPuuid, String yourPuuid) {
        Mono<List<String>> myMatchesMono = getMatchId(myPuuid);
        Mono<List<String>> yourMatchesMono = getMatchId(yourPuuid);

        return Mono.zip(myMatchesMono, yourMatchesMono)
                .map(tuple -> {
                    Map<String, List<String>> resultMap = new HashMap<>();
                    resultMap.put("myMatches", tuple.getT1());
                    resultMap.put("yourMatches", tuple.getT2());
                    return resultMap;
                });
    }
    private Mono<List<String>> getMatchId(String puuid) {
        try {
            String requestURL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=20&api_key=" + apiKey;
            return client.get()
                    .uri(requestURL)
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(this::parseMatchIds);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
    private List<String> parseMatchIds(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            System.out.println(" json node " + jsonNode );
            return objectMapper.convertValue(jsonNode, List.class);
        } catch (Exception e) {
            throw new RuntimeException("parseUserId e ", e);
        }
    }

    public Flux<Map> getMatchDetails(List<String> matchIds) { // flux 스트림 반환
        return Flux.fromIterable(matchIds) // flux 생성
                .flatMap(matchId -> {
                    String requestURL = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + apiKey;
                    return client.get()
                            .uri(requestURL)
                            .retrieve()
                            .bodyToMono(Map.class)
                            .flux();
                }, 4) // 요청 최대 5개
                .onErrorResume(e -> Flux.error(e));
    }
}
