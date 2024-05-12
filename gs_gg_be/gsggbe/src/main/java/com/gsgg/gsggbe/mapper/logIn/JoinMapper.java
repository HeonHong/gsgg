package com.gsgg.gsggbe.mapper.logIn;

import com.gsgg.gsggbe.login.dto.JoinDTO;

public interface JoinMapper {


    int isUserExist(String username);

    int saveUser(JoinDTO joinDTO);

}
