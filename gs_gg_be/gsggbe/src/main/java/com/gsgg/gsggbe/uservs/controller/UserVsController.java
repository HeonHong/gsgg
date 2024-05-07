package com.gsgg.gsggbe.uservs.controller;

import com.gsgg.gsggbe.uservs.service.UserVsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserVsController {

    private final UserVsService userVsService;

    @GetMapping("/getUserInfo")
    public void getUserInfo() {
        System.out.println("getUserInfo 확인");
        this.userVsService.selectUserInfo("hide on bush", "KR1").subscribe(
                result -> System.out.println("Response: " + result), // 성공한 경우의 동작
                error -> System.err.println("Error: " + error) // 에러 발생 시의 동작
        );
    }
}
