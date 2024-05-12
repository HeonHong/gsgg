package com.gsgg.gsggbe.uservs.service;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserVsService {
    // 종욱님 key
    private final String apiKey = "RGAPI-3c872789-bc9e-4668-b5f1-ad8d49383207";

    // WebClient의 인스턴스 생성
    private final WebClient client = WebClient.create();

    public Mono<String> getUserPuuid(String summonerName, String region) {
        try {
            String requestURL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + summonerName + "/" + region
                    +"?api_key=" + apiKey;
            return client.get()
                    .uri(requestURL) // URI 주소 설정
                    .retrieve()
                    .bodyToMono(String.class) // 비동기 mono로 변환
                    .doOnNext(result -> System.out.println("Response: " + result))
                    .doOnError(error -> System.err.println("Error: " + error));
        } catch (Exception e) {
            return Mono.error(e);
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
    public Mono<String> getMatchId(String puuid) {
        try {
            String requestURL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=20&api_key=" + apiKey;
            return client.get()
                    .uri(requestURL)
                    .retrieve()
                    .bodyToMono(String.class);
        } catch ( Exception e ) {
            return Mono.error(e);
        }
    }
    public Mono<String> getMatchDetails() {
        try {
            String requestURL = "https://asia.api.riotgames.com/lol/match/v5/matches/" + "param 에서가져온 matchid"
                    +"?api_key=" + apiKey;
            return client.get()
                    .uri(requestURL)
                    .retrieve()
                    .bodyToMono(String.class);
        } catch ( Exception e ) {
            return Mono.error(e);
        }
    }

}
