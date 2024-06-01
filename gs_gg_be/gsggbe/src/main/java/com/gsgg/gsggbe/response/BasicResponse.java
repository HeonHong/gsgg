package com.gsgg.gsggbe.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

@Getter
@AllArgsConstructor
//public class BasicResponse<T> extends ResponseEntity<T> {
public class BasicResponse<T> {

    private final int statusCode;
    private final String msg;
    private final T body;

    @Builder
    public BasicResponse(T body ,ResponseType responseType){
//        super(body, responseType.getStatusCode());
        this.body = body;
        this.statusCode = responseType.getStatusCode();
        this.msg = responseType.getMsg();
    }

}
