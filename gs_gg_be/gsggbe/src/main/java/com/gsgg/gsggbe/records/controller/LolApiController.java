package com.gsgg.gsggbe.records.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
public class LolApiController {

    private final WebClient webClient;
    private String apiKey = "RGAPI-e274a55a-e41b-42e0-b50d-1e63bdac3248"; // API 키

    public LolApiController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://kr.api.riotgames.com").build();
    }

    // 소환사 이름으로 소환사 정보를 조회
    @GetMapping("/summoner")
    public Mono<Map> getSummonerInfo(@RequestParam(value="summonerName") String summonerName) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/lol/summoner/v4/summoners/by-name/{summonerName}")
                        .queryParam("api_key", apiKey)
                        .build(summonerName))
                .retrieve()
                .bodyToMono(Map.class);
    }

    // PUUID로 Match 정보 조회
    @GetMapping("/matches")
    public Mono<List> getMatchInfo(@RequestParam(value="puuid") String puuid) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/lol/match/v5/matches/by-puuid/{puuid}/ids")
                        .queryParam("api_key", apiKey)
                        .build(puuid))
                .retrieve()
                .bodyToMono(List.class); // 매치 ID 목록을 반환
    }
}