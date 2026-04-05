package com.study.service;

import com.study.dto.TokenResponse;
import com.study.entity.Credential;
import com.study.repository.CredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CredentialServiceImpl implements CredentialService {

    private final CredentialRepository credentialRepository;

    private static final long FIVE_MINUTES_MS = 5 * 60 * 1000;
    private static final long TOKEN_LIFETIME_MS = 60 * 60 * 1000;

    @Override
    public TokenResponse getTokenBySeries(String series) {

        Date now = new Date();
        Date threshold = new Date(now.getTime() + FIVE_MINUTES_MS);

        Credential credential = credentialRepository.findBySeries(series)
                .map(existing -> {
                    if (existing.getExpiredDate().before(threshold)) {
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

    private Credential createNewToken(String series, Date now) {
        Credential newCred = Credential.builder()
                .series(series)
                .token(generateToken())
                .expiredDate(new Date(now.getTime() + TOKEN_LIFETIME_MS))
                .active(true)
                .build();
        return credentialRepository.save(newCred);
    }

    private Credential updateExistingToken(Credential credential, Date now) {
        credential.setToken(generateToken());
        credential.setExpiredDate(new Date(now.getTime() + TOKEN_LIFETIME_MS));
        credential.setUpdatedDate(now);
        return credentialRepository.save(credential);
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }
}
