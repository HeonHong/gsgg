package com.gsgg.gsggbe.login.service;

import com.gsgg.gsggbe.login.constants.RoleConstants;
import com.gsgg.gsggbe.login.dto.JoinDTO;
import com.gsgg.gsggbe.mapper.logIn.JoinMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JoinService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JoinMapper joinMapper;
    public int joinProcess(JoinDTO joinDTO){
//        String userId = joinDTO.getUsername();
//        String name = joinDTO.getName();
//        String password = joinDTO.getPassword();
//        int isExist = joinMapper.isExist(userId);
//        if (isExist!=0){
//            return 0;
//        }

        //BCryptPasswordEncoder로 비밀번호 해싱처리
        joinDTO.setPassword(bCryptPasswordEncoder.encode((CharSequence) joinDTO.getPassword()));
//어드민 계정 만들기 분리 예정
        //        joinDTO.setRole(RoleConstants.ADMINISTRATOR);

        int isSuccess;

        try{
            isSuccess= joinMapper.saveUser(joinDTO);

        }catch (Exception e){
            log.info("회원 가입 중 에러 발생 {}", e.getMessage());
            return 0;
        }
        System.out.println(isSuccess);
        return isSuccess;
    }
}
