package com.twin.baribari.auth.infrastructure;

import com.twin.baribari.auth.domain.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

    private static final String MEMBER_ID_CLAIM = "memberId";

    private final SecretKey secretKey;
    private final long accessTokenExpiryMs;
    private final long refreshTokenExpiryMs;
    private final Clock clock;

    public JwtTokenProvider(
        @Value("${jwt.secret}") final String secret,
        @Value("${jwt.access-token-expiry-minutes}") final long accessTokenExpiryMinutes,
        @Value("${jwt.refresh-token-expiry-days}") final long refreshTokenExpiryDays,
        final Clock clock
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiryMs = accessTokenExpiryMinutes * 60 * 1000;
        this.refreshTokenExpiryMs = refreshTokenExpiryDays * 24 * 60 * 60 * 1000;
        this.clock = clock;
    }

    @Override
    public String createAccessToken(final Long memberId) {
        return buildToken(memberId, accessTokenExpiryMs);
    }

    @Override
    public String createRefreshToken(final Long memberId) {
        return buildToken(memberId, refreshTokenExpiryMs);
    }

    @Override
    public Long extractMemberId(final String token) {
        return parseClaims(token).get(MEMBER_ID_CLAIM, Long.class);
    }

    @Override
    public boolean isValid(final String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private String buildToken(final Long memberId, final long expiryMs) {
        final Date now = Date.from(clock.instant());
        return Jwts.builder()
            .claim(MEMBER_ID_CLAIM, memberId)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + expiryMs))
            .signWith(secretKey)
            .compact();
    }

    private Claims parseClaims(final String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
