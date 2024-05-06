package com.gsgg.gsggbe.login.controller;

import com.gsgg.gsggbe.login.dto.JoinDTO;
import com.gsgg.gsggbe.login.service.JoinService;
import com.gsgg.gsggbe.mapper.logIn.LogInMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/check-id")
    public int checkId(@RequestParam String username) {
        if (username.equals("")) return 1;
        
        int isUser = joinService.isUserExist(username);
        return isUser;

    }

    @PostMapping("/join")
    public String join(@RequestBody JoinDTO joinDTO) {
        int isSucess = joinService.joinProcess(joinDTO);
        if (isSucess == 1) return "성공하였습니다.";
        return "실패하였습니다.";
    }

}
