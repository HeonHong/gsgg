package com.gsgg.gsggbe.mapper.log;

import com.gsgg.gsggbe.log.entity.LogEntity;

import java.util.List;
import java.util.Map;

public interface LogMapper {
    List<Map<String, Object>> insertLogTest(LogEntity logEntity);
}
