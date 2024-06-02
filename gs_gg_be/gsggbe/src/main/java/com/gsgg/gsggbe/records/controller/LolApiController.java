package com.gsgg.gsggbe.records.controller;

import com.gsgg.gsggbe.records.service.LolApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
public class LolApiController {

    private final LolApiService lolApiService;

    @Autowired
    public LolApiController(LolApiService lolApiService) {
        this.lolApiService = lolApiService;
    }

    @GetMapping("/summonerId")
    public Mono<Map> getSummonerPuuid(@RequestParam(value="summonerName") String summonerName,
                                      @RequestParam(value="tagLine") String tagLine) {
        return lolApiService.getSummonerPuuid(summonerName, tagLine);
    }

    @GetMapping("/summonerInfo")
    public Mono<Map> getSummonerInfo(@RequestParam(value="puuid") String puuid) {
        return lolApiService.getSummonerInfo(puuid);
    }

    @GetMapping("/matches")
    public Mono<List> getMatchIds(@RequestParam(value="puuid") String puuid) {
        return lolApiService.getMatchIds(puuid);
    }

    @GetMapping("/matches/details")
    public Flux<Map> getMatchDetails(@RequestParam List<String> matchIds) {
        return lolApiService.getMatchDetails(matchIds);
    }
}