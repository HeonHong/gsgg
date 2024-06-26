package com.gsgg.gsggbe.mapper.characters;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CrtMainMapper {

    List<Map<String, Object>> selectCrtInfos();

    void mergeCrtBasicInfo(Map<String, Object> stringObjectMap);

    void mergeCrtDescInfo(Map<String, Object> stringObjectMap);

    void mergeCrtImg(Map<String, Object> stringObjectMap);
}
