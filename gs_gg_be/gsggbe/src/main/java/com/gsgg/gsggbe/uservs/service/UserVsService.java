package com.gsgg.gsggbe.uservs.service;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.http.HttpClient;

@Service
public class UserVsService {
    private String apiKey = "RGAPI-3c872789-bc9e-4668-b5f1-ad8d49383207";
    public void selectUserInfo() {
        String SummonerName = "hideonbush"; // 닉네임
        String requestURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+
                SummonerName + "?api_key=" + apiKey;
        System.out.println(" selectUserInfo");
        try {
            WebClient client = WebClient.create();
            Mono<String> stringMono = client.get()
                    .uri(requestURL)
                    .retrieve()
                    .bodyToMono(String.class);
            System.out.println(stringMono.flux().toStream().findFirst());
            System.out.println("종료");
        } catch ( Exception e ) {
            System.out.println(" error ee " + e);
        }
    }
}
