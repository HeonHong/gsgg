package com.gsgg.gsggbe.test.mbTest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class MbTest {

    private final MbService mbService;

    @GetMapping("/api/test")
    public List<Map<String, Object>> mbtest() {
      return this.mbService.mbtest();
    }
}
