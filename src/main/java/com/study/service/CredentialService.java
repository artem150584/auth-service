package com.study.service;

import com.study.dto.TokenResponse;

public interface CredentialService {
    TokenResponse getTokenBySeries(String series);
}
