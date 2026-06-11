package com.twin.baribari.auth.domain;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class RefreshToken {

    private final Long id;
    private final Long memberId;
    private final String token;
    private final LocalDateTime expiresAt;

    public RefreshToken(
        final Long id,
        final Long memberId,
        final String token,
        final LocalDateTime expiresAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.token = token;
        this.expiresAt = expiresAt;

        validateArguments();
    }

    public RefreshToken(
        final Long memberId,
        final String token,
        final LocalDateTime expiresAt
    ) {
        this(null, memberId, token, expiresAt);
    }

    private void validateArguments() {
        Objects.requireNonNull(memberId, "회원 ID는 필수값입니다.");
        Objects.requireNonNull(token, "토큰은 필수값입니다.");
        Objects.requireNonNull(expiresAt, "만료 시간은 필수값입니다.");
    }

    public boolean isExpired(final Clock clock) {
        return !LocalDateTime.now(clock).isBefore(expiresAt);
    }

    public boolean isStolen(final String requestToken) {
        return !token.equals(requestToken);
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final RefreshToken that = (RefreshToken) other;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
