package com.gsgg.gsggbe.characters.service;

import com.gsgg.gsggbe.mapper.characters.CrtMainMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CrtMainService {

    private final CrtMainMapper crtMainMapper;

    public List<Map<String, Object>> selectCrtInfos() {

        return this.crtMainMapper.selectCrtInfos();
    }
}
