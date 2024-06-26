package com.gsgg.gsggbe.records.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
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

    // 닉네임과 태그라인으로 Puuid를 조회
    @GetMapping("/summonerId")
    public Mono<Map> getSummonerPuuid(@RequestParam(value="summonerName") String summonerName,
                                     @RequestParam(value="tagLine") String tagLine) {
        return this.webClientAsia.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/riot/account/v1/accounts/by-riot-id/{summonerName}/{tagLine}")
                        .queryParam("api_key", apiKey)
                        .build(summonerName,tagLine))
                .retrieve()
                .bodyToMono(Map.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(this::isRateLimitException));
    }

    // Puuid로 소환사 정보를 조회
    @GetMapping("/summonerInfo")
    public Mono<Map> getSummonerInfo(@RequestParam(value="puuid") String puuid) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/lol/summoner/v4/summoners/by-puuid/{puuid}")
                        .queryParam("api_key", apiKey)
                        .build(puuid))
                .retrieve()
                .bodyToMono(Map.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(this::isRateLimitException));
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
                .bodyToMono(List.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(this::isRateLimitException));
    }

    @GetMapping("/matches/details")
    public Flux<Map> getMatchDetails(@RequestParam List<String> matchIds) {
        return Flux.fromIterable(matchIds)
                .delayElements(Duration.ofMillis(100))
                .flatMap(matchId -> this.webClientAsia.get()
                                .uri(uriBuilder -> uriBuilder
                                        .path("/lol/match/v5/matches/{matchId}")
                                        .queryParam("api_key", apiKey)
                                        .build(matchId))
                                .retrieve()
                                .bodyToMono(Map.class)
                                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                                        .filter(this::isRateLimitException))
                        );
    }

    private boolean isRateLimitException(Throwable throwable) {
        return throwable instanceof WebClientResponseException &&
                ((WebClientResponseException) throwable).getStatusCode() == HttpStatus.TOO_MANY_REQUESTS;
    }
}