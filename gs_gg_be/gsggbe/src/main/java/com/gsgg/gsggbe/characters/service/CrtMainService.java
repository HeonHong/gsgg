package com.gsgg.characters.service;

import com.gsgg.characters.mapper.CrtMainMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class CrtMainService {

    private final CrtMainMapper crtMainMapper;

    public List<Map<String, Object>> selectCrtInfos() {
        return this.crtMainMapper.selectCrtInfos();
    }
}
