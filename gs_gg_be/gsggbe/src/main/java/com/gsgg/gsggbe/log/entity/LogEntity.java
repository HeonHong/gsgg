package com.gsgg.gsggbe.log.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity {
    private Long id;
    private String methodName;
    private String timeStamp;
}

