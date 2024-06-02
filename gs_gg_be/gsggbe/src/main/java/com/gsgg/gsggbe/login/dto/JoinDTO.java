package com.gsgg.gsggbe.login.dto;

import com.gsgg.gsggbe.login.constants.RoleConstants;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

//회원 가입용 DTO
@Data
public class JoinDTO {
    //private String userId;
    @NotEmpty
    private String name;
    //아이디
    @NotEmpty
    private String username;
    @NotEmpty
    private String birthday;
    @NotEmpty
    private String mobileNum;
    @NotEmpty
    private String password;
    private String role;


    public JoinDTO(){
        this.role = RoleConstants.CLIENT;
    }
}
