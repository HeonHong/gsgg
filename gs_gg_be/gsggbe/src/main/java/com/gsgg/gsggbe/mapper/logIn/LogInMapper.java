package com.gsgg.gsggbe.mapper.logIn;

import com.gsgg.gsggbe.login.dto.UserDTO;

public interface LogInMapper {
    UserDTO findUser(String username);


    String getUsername(String id);
}
