package com.gsgg.gsggbe.login.service;

import com.gsgg.gsggbe.login.dto.JoinDTO;
import com.gsgg.gsggbe.mapper.logIn.JoinMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JoinService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JoinMapper joinMapper;
    public void joinProcess(JoinDTO joinDTO){
        String userId = joinDTO.getUserId();
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        int isExist = joinMapper.isExist(userId);
        if (isExist!=0){
            System.out.println("여긴 들어오면 안되는데");
            return;
        }
        joinDTO.setPassword(bCryptPasswordEncoder.encode((CharSequence) joinDTO.getPassword()));
        joinDTO.setRole("ROLE_ADMIN");

        joinMapper.saveUser(joinDTO);
    }
}
