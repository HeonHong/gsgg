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
        System.out.println("userInfo");
        this.userVsService.selectUserInfo();
    }
}
