package com.gsgg.gsggbe.mapper.characters;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CrtMainMapper {

    List<Map<String, Object>> selectCrtInfos();

    void mergeCrtinfos(Map<String, Object> championList);

    void mergeCrtBasicInfo(Map<String, Object> stringObjectMap);
}
