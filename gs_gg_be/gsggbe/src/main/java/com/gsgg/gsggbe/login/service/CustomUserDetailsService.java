package com.gsgg.gsggbe.login.service;

import com.gsgg.gsggbe.login.dto.CustomUserDetails;
import com.gsgg.gsggbe.login.dto.UserDTO;
import com.gsgg.gsggbe.mapper.logIn.LogInMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LogInMapper loginMapper;

    public CustomUserDetailsService(LogInMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("들어옴?");
        UserDTO userDTO = loginMapper.findUser(username);
        System.out.println(userDTO);
        if(userDTO!=null){
            return new CustomUserDetails(userDTO);
        }
        return null;
    }
}
