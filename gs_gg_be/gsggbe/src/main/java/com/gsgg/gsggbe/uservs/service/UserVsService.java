package com.gsgg.gsggbe.uservs.service;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserVsService {
    // 종욱님 key
    private final String apiKey = "RGAPI-3c872789-bc9e-4668-b5f1-ad8d49383207";

    public Mono<String> selectUserInfo(String summonerName, String region) {
        try {
            String requestURL = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + summonerName + "/" + region
                    +"?api_key=" + apiKey;
            WebClient client = WebClient.create();
            return client.get()
                    .uri(requestURL)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnNext(result -> System.out.println("Response: " + result)) // 성공
                    .doOnError(error -> System.err.println("Error: " + error)); // 실패
        } catch (Exception e) {
            System.out.println(" 에러¡ ");
            return Mono.error(e);
        }
    }

}
