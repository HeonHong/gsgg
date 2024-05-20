package com.gsgg.gsggbe.login.controller;

import com.gsgg.gsggbe.login.dto.JoinDTO;
import com.gsgg.gsggbe.login.service.JoinService;
import com.gsgg.gsggbe.mapper.logIn.LogInMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/check-id")
    public ResponseEntity checkId(@RequestParam String username) {
        if (username.isEmpty()) return ResponseEntity.badRequest().body("정보가 누락되었습니다.");
        int isUser = joinService.isUserExist(username);
        return ResponseEntity.ok().body(isUser);
    }

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody @Valid JoinDTO joinDTO, BindingResult bd) {
        if (bd.hasErrors()) return ResponseEntity.badRequest().body("정보가 누락되었습니다.");
        int isSucess = joinService.joinProcess(joinDTO);
        if (isSucess == 1) return ResponseEntity.ok().body("가입에 성공하였습니다");
        return ResponseEntity.badRequest().body("가입에 실패하였습니다.");
    }

}
