package com.gsgg.gsggbe.test.mbTest;

import com.gsgg.gsggbe.mapper.test.MbMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MbService {

    private final MbMapper mbMapper;

    public List<Map<String, Object>> mbtest() {
        return this.mbMapper.mbtest();
    }
}
