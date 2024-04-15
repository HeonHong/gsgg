package com.gsgg.gsggbe.mapper.logIn;

import com.gsgg.gsggbe.login.dto.JoinDTO;

public interface JoinMapper {


    int isExist(String userId);

    void saveUser(JoinDTO joinDTO);
}
