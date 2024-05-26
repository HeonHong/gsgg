package com.gsgg.gsggbe.uservs.controller;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.gsgg.gsggbe.uservs.dto.MatchIdsDTO;
import com.gsgg.gsggbe.uservs.service.UserVsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UserVsController {

    private final UserVsService userVsService;

    // puuid 조회
    @GetMapping("/getUserPuuid")
    public Mono<Map<String, String>> getUserPuuid(@RequestParam("mySummonerName") String mySummonerName,
                                    @RequestParam("yourSummonerName") String yourSummonerName,
                                    @RequestParam("tagLine") String tagLine) {
        return this.userVsService.getUserPuuid(mySummonerName, yourSummonerName, tagLine);
    }

    // puuid 로 id 조회
    @GetMapping("/getUserId")
    public Mono<String> getUserId( @RequestParam String puuid){
        return this.userVsService.getUserId(puuid)
                .doOnNext( result -> System.out.println( " res " + result))
                .doOnError(error -> System.err.println("Error: " + error));
    }

    // id 로 전적 검색
//    @GetMapping("/getSummonerInfo")
//    public Mono<String> getSummonerInfo( @RequestParam String id){
//        return this.userVsService.getSummonerInfo(id)
//                .doOnNext( result -> System.out.println( " res " + result))
//                .doOnError(error -> System.err.println("Error: " + error));
//    }

    @GetMapping("/getMatchId")
    public Mono<Map<String, List<String>>> getMatchIds(@RequestParam String myPuuid, @RequestParam String yourPuuid) {
        return this.userVsService.getMatchIds(myPuuid, yourPuuid)
                .doOnNext( result -> System.out.println( " res " + result))
                .doOnError(error -> System.err.println("Error: " + error));
    }

    @PostMapping ("/getMatchDetails")
    public Mono<Map<String, List<Map>>> getMatchDetails(@RequestBody MatchIdsDTO matchIdsDTO) {
        List<String> myMatches = matchIdsDTO.getMyMatches();
        List<String> yourMatches = matchIdsDTO.getYourMatches();
        return this.userVsService.getMatchDetails( myMatches,  yourMatches);
    }
}
