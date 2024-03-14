package com.gsgg.characters.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CrtMainMapper {
    List<Map<String, Object>> selectCrtInfos();

}
