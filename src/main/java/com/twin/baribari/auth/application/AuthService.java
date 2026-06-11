package com.twin.baribari.auth.application;

import com.twin.baribari.auth.application.dto.TokenResponse;
import com.twin.baribari.auth.domain.RefreshToken;
import com.twin.baribari.auth.domain.RefreshTokenRepository;
import com.twin.baribari.auth.domain.TokenProvider;
import com.twin.baribari.auth.domain.exception.InvalidRefreshTokenException;
import com.twin.baribari.auth.domain.exception.RefreshTokenStolenException;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final Clock clock;

    @Value("${jwt.refresh-token-expiry-days}")
    private long refreshTokenExpiryDays;

    public String issueAccessToken(final long memberId) {
        return tokenProvider.createAccessToken(memberId);
    }

    @Transactional
    public String issueRefreshToken(final long memberId) {
        final String refreshToken = tokenProvider.createRefreshToken(memberId);
        refreshTokenRepository.save(
            new RefreshToken(
                memberId,
                refreshToken,
                LocalDateTime.now(clock)
                    .plusDays(refreshTokenExpiryDays)
            )
        );
        return refreshToken;
    }

    @Transactional
    public TokenResponse reissueTokens(final String requestRefreshToken) {
        final Long memberId = tokenProvider.extractMemberId(requestRefreshToken);

        final RefreshToken stored = refreshTokenRepository.findByMemberId(memberId)
            .orElseThrow(InvalidRefreshTokenException::new);

        if (stored.isStolen(requestRefreshToken)) {
            refreshTokenRepository.deleteByMemberId(memberId);
            throw new RefreshTokenStolenException();
        }

        if (stored.isExpired(clock)) {
            throw new InvalidRefreshTokenException();
        }

        final String newAccessToken = issueAccessToken(memberId);
        final String newRefreshToken = issueRefreshToken(memberId);
        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    @Transactional
    public void revokeToken(final long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
    }
}
