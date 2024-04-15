package com.gsgg.gsggbe.test.mbTest;

import com.gsgg.gsggbe.mapper.test.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    @Autowired
    TestMapper testMapper;
    public String test() {
        List<String> testResponse = testMapper.selectTest();
        return null;
    }
}
