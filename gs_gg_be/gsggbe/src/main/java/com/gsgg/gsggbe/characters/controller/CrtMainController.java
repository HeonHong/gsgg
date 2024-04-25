package com.gsgg.gsggbe.characters.controller;

import com.gsgg.gsggbe.characters.service.CrtMainService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class CrtMainController {

    private final CrtMainService crtMainService;

    @GetMapping("/api/getCrtInfos")
    public void getCrtInfos() {
        this.crtMainService.selectCrtInfos();
    }
}
