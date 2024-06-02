package com.gsgg.gsggbe.response;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ResponseType {

    SUCCESS(200,"요청을 성공하였습니다."),
    LOGIN_FAIL(401,"잘못된 정보입니다"),
    LOGIN_NO_RELATED_ID(401,"기존 정보와 연동된 정보가 없습니다. 회원 가입하시겠습니까?");

    private int statusCode;
    private String msg;

    ResponseType(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

}
