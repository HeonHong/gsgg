package com.gsgg.gsggbe.mapper.log;

import com.gsgg.gsggbe.log.entity.LogEntity;

import java.util.List;
import java.util.Map;

public interface LogMapper {
    void insertDbLog(LogEntity logEntity);
}
