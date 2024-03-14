package com.gsgg.characters.controller;

import com.gsgg.characters.service.CrtMainService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class CrtMainController {

    private final CrtMainService crtMainService;

    @GetMapping("/api/getCrtInfos")
    public List<Map<String, Object>> getCrtInfos() {
        log.info("getCrtInfos");
        return this.crtMainService.selectCrtInfos();
    }
}
