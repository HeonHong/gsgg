package com.gsgg.gsggbe.test.mbTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class LolApiController {

    private final WebClient webClient;
    private String apiKey = "RGAPI-b44ea6fd-ff2d-4976-a09a-5857daac2787"; // API 키

    public LolApiController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://kr.api.riotgames.com").build();
    }

    // 소환사 이름으로 소환사 정보를 조회하는 GET 메소드
    @GetMapping("/api/lol/summoner")
    public Mono<Map> getSummonerInfo(@RequestParam(value="summonerName") String summonerName) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/lol/summoner/v4/summoners/by-name/{summonerName}")
                        .queryParam("api_key", apiKey)
                        .build(summonerName))
                .retrieve()
                .bodyToMono(Map.class);
    }
}