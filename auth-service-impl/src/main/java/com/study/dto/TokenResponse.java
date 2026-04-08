package com.study.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class TokenResponse {

    private UUID id;
    private String series;
    private LocalDateTime expiredDate;
    private String token;

}
