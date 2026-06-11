package com.twin.baribari.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RefreshTokenTest {

    private static final Long MEMBER_ID = 1L;
    private static final String TOKEN = "stored-refresh-token";

    private static final Clock FIXED_CLOCK = Clock.fixed(
        Instant.parse("2026-06-11T00:00:00Z"),
        ZoneId.of("Asia/Seoul")
    );

    @Nested
    @DisplayName("토큰의 만료 여부를 조회한다")
    class IsExpired {

        @Test
        void 만료_시간이_현재보다_과거이면_만료된_토큰이다() {
            // given
            final LocalDateTime expiresAt = LocalDateTime.now(FIXED_CLOCK).minusSeconds(1);
            final RefreshToken token = new RefreshToken(
                MEMBER_ID,
                TOKEN,
                expiresAt
            );

            // when
            final boolean expired = token.isExpired(FIXED_CLOCK);

            // then
            assertThat(expired).isTrue();
        }

        @Test
        void 만료_시간이_현재와_같으면_만료된_토큰이다() {
            // given
            final LocalDateTime expiresAt = LocalDateTime.now(FIXED_CLOCK);
            final RefreshToken token = new RefreshToken(
                MEMBER_ID,
                TOKEN,
                expiresAt
            );

            // when
            final boolean expired = token.isExpired(FIXED_CLOCK);

            // then
            assertThat(expired).isTrue();
        }

        @Test
        void 만료_시간이_현재보다_미래이면_유효한_토큰이다() {
            // given
            final LocalDateTime expiresAt = LocalDateTime.now(FIXED_CLOCK).plusDays(7);
            final RefreshToken token = new RefreshToken(
                MEMBER_ID,
                TOKEN,
                expiresAt
            );

            // when
            final boolean expired = token.isExpired(FIXED_CLOCK);

            // then
            assertThat(expired).isFalse();
        }
    }

    @Nested
    @DisplayName("토큰의 탈취 여부를 조회한다")
    class IsStolen {

        @Test
        void 요청_토큰이_저장된_토큰과_다르면_탈취된_토큰이다() {
            // given
            final RefreshToken stored = new RefreshToken(
                MEMBER_ID,
                TOKEN,
                LocalDateTime.now(FIXED_CLOCK).plusDays(7)
            );

            final String requestToken = "different-token";

            // when
            final boolean isStolen = stored.isStolen(requestToken);

            // then
            assertThat(isStolen).isTrue();
        }

        @Test
        void 요청_토큰이_저장된_토큰과_같으면_탈취되지_않은_토큰이다() {
            // given
            final RefreshToken stored = new RefreshToken(
                MEMBER_ID,
                TOKEN,
                LocalDateTime.now(FIXED_CLOCK).plusDays(7)
            );

            // when
            final boolean isStolen = stored.isStolen(stored.getToken());

            // then
            assertThat(isStolen).isFalse();
        }
    }
}
