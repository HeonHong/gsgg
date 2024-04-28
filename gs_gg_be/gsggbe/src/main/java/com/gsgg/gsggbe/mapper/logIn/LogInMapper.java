package com.gsgg.gsggbe.mapper.logIn;

import com.gsgg.gsggbe.login.dto.UserDTO;

public interface LogInMapper {
    int isMember(String id);
    UserDTO findUser(String username);
}
