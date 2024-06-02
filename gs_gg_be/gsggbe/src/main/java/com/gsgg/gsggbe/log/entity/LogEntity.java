package com.gsgg.gsggbe.log.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity {
    private String userId;
    private String ip;
    private String methodName;
    private String timeStamp;
}

