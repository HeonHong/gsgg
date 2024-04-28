package com.gsgg.gsggbe.login.dto;

import lombok.Data;

//회원 가입용 DTO
@Data
public class JoinDTO {
    private String userId;
    private String username;
    private String password;
    private String role;
}
