package com.gsgg.gsggbe.login.dto;

import com.gsgg.gsggbe.login.constants.RoleConstants;
import lombok.Data;

//회원 가입용 DTO
@Data
public class JoinDTO {
//    private String userId;
    private String name;
    //아이디
    private String username;
    private String birthday;
    private String mobileNum;
    private String password;
    private String role;



    public JoinDTO(){
        this.role = RoleConstants.CLIENT;
    }
}
