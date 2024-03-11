package com.gsgg.gsggbe.test.mbTest;


//import miniPorject.gs_gg.mbTest.TestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/api")
    public String testMethod(@RequestParam(value="test") String test) {
        System.out.println("getTest"+test);
//        String testResponse = testService.test();
        return "test view";
    }
    @GetMapping("/getTest")
    public String getTestMethod() {
        System.out.println("getTest");
//        String testResponse = testService.test();
        return "test view";
    }

//    @GetMapping("/api/test")
    public Map<String, Object> testMethod2() {

        return Map.of("data1", "data1",
                        "data2", 2,
                        "data3", true);
    }

    @PostMapping("/post")
    public String postTest(@RequestBody Map<String, String>test){
        return "post 성공";
    }
    @PutMapping("/putTest")
    public String putTest(@RequestBody Map<String, String>test){
//        System.out.println("putTest"+test);
        return "put 성공";
    }

    @DeleteMapping("/delTest")
    public String delTest(@RequestParam String test){
        System.out.println("delTest"+test);
        return "delete 성공";
    }
}
