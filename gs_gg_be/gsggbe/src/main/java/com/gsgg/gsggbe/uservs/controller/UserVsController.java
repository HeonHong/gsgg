package com.gsgg.gsggbe.uservs.controller;

import com.gsgg.gsggbe.uservs.service.UserVsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class UserVsController {

    private final UserVsService userVsService;

    // puuid 조회
    @GetMapping("/getUserPuuid")
    public Mono<String> getUserPuuid(@RequestParam("mySummonerName") String mySummonerName,
                                    @RequestParam("tagLine") String tagLine) {
        System.out.println("getUserInfo 확인");
        System.out.println("getUserInfo 확인 mySummonerName " + mySummonerName);
        System.out.println("getUserInfo 확인 tagLine " + tagLine);
        return this.userVsService.getUserPuuid(mySummonerName, tagLine)
                .doOnNext(result -> System.out.println("Response: " + result))
                .doOnError(error -> System.err.println("Error: " + error));
    }

    // puuid 로 id 조회
    @GetMapping("/getUserId")
    public Mono<String> getUserId( @RequestParam String puuid){
        return this.userVsService.getUserId(puuid)
                .doOnNext( result -> System.out.println( " res " + result))
                .doOnError(error -> System.err.println("Error: " + error));
    }

    // id 로 전적 검색
    @GetMapping("/getSummonerInfo")
    public Mono<String> getSummonerInfo( @RequestParam String id){
        return this.userVsService.getSummonerInfo(id)
                .doOnNext( result -> System.out.println( " res " + result))
                .doOnError(error -> System.err.println("Error: " + error));
    }

    @GetMapping("/getMatchId")
    public Mono<String> getMatchId( @RequestParam String puuid){
        return this.userVsService.getMatchId(puuid)
                .doOnNext( result -> System.out.println( " res " + result))
                .doOnError(error -> System.err.println("Error: " + error));
    }

}
