package com.gsgg.gsggbe.login.controller;

import com.gsgg.gsggbe.login.dto.JoinDTO;
import com.gsgg.gsggbe.login.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO){
        int isSucess=joinService.joinProcess(joinDTO);
        if(isSucess==1) return "성공하였습니다.";

        return "실패하였습니다.";
    }

}
