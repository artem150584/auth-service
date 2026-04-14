package com.study.controller;

import com.study.service.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final CredentialService credentialService;

    @Override
    public String getToken(String series) {

        return credentialService.getTokenBySeries(series).getToken();
    }
}
