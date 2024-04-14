package com.gsgg.gsggbe.records.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
public class LolApiController {

    private final WebClient webClient;
    private final WebClient webClientAsia;
    private String apiKey = "RGAPI-3c872789-bc9e-4668-b5f1-ad8d49383207"; // API 키

    public LolApiController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://kr.api.riotgames.com").build();
        this.webClientAsia = webClientBuilder.baseUrl("https://asia.api.riotgames.com").build();
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

    // PUUID로 Match ID 조회
    @GetMapping("/matches")
    public Mono<List> getMatchIds(@RequestParam(value="puuid") String puuid) {
        return this.webClientAsia.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/lol/match/v5/matches/by-puuid/{puuid}/ids")
                        .queryParam("api_key", apiKey)
                        .build(puuid))
                .retrieve()
                .bodyToMono(List.class);
    }

    @GetMapping("/matches/details")
    public Flux<Map> getMatchDetails(@RequestParam List<String> matchIds) {
        return Flux.fromIterable(matchIds)
                .flatMap(matchId -> this.webClientAsia.get()
                                .uri(uriBuilder -> uriBuilder
                                        .path("/lol/match/v5/matches/{matchId}")
                                        .queryParam("api_key", apiKey)
                                        .build(matchId))
                                .retrieve()
                                .bodyToMono(Map.class),
                        5); // 동시 요청 처리 제한
    }
}