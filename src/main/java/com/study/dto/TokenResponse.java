package com.study.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class TokenResponse {

    private UUID id;
    private String series;
    private Date expiredDate;
    private String token;

}
