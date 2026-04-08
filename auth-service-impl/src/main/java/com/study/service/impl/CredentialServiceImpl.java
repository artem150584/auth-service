package com.study.service.impl;

import com.study.dto.TokenResponse;
import com.study.entity.Credential;
import com.study.repository.CredentialRepository;
import com.study.service.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CredentialServiceImpl implements CredentialService {

    private final CredentialRepository credentialRepository;


    @Value("${application.token.expiry-threshold:PT5M}")
    private Duration expiryThreshold;

    @Value("${application.token.lifetime:PT1H}")
    private Duration tokenLifeTime;

    @Override
    public TokenResponse getTokenBySeries(String series) {

        LocalDateTime  now = LocalDateTime.now();
        LocalDateTime  threshold = now.plus(expiryThreshold);

        Credential credential = credentialRepository.findBySeries(series)
                .map(existing -> {
                    if (existing.getExpiredDate().isBefore(threshold)) {
                        return updateExistingToken(existing, now);
                    }
                    return existing;
                })
                .orElseGet(() -> createNewToken(series, now));

        return TokenResponse.builder()
                .id(credential.getId())
                .token(credential.getToken())
                .series(credential.getSeries())
                .expiredDate(credential.getExpiredDate())
                .build();
    }

    private Credential createNewToken(String series, LocalDateTime now) {
        Credential newCred = Credential.builder()
                .series(series)
                .token(generateToken())
                .expiredDate(now.plus(tokenLifeTime))
                .active(true)
                .updatedDate(now)
                .build();
        return credentialRepository.save(newCred);
    }

    private Credential updateExistingToken(Credential credential, LocalDateTime now) {
        credential.setToken(generateToken());
        credential.setExpiredDate(now.plus(tokenLifeTime));
        credential.setUpdatedDate(now);
        return credentialRepository.save(credential);
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }
}
