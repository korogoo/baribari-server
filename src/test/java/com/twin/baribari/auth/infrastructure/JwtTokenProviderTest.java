package com.twin.baribari.auth.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.twin.baribari.config.TestClockConfig;
import java.time.Clock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private static final String SECRET = "baribari-secret-key-must-be-at-least-32-bytes-long!!";
    private static final Long MEMBER_ID = 1L;

    private final JwtTokenProvider tokenProvider = new JwtTokenProvider(
        SECRET, 15L, 7L, TestClockConfig.FIXED_CLOCK
    );

    @Nested
    @DisplayName("AccessToken을 발급한다")
    class CreateAccessToken {

        @Test
        void AT_생성_후_memberId를_추출한다() {
            // when
            final String token = tokenProvider.createAccessToken(MEMBER_ID);

            // then
            final Long memberId = tokenProvider.extractMemberId(token);
            assertThat(memberId).isEqualTo(MEMBER_ID);
        }

        @Test
        void 발급된_AT는_유효하다() {
            // when
            final String token = tokenProvider.createAccessToken(MEMBER_ID);

            // then
            assertThat(tokenProvider.isValid(token)).isTrue();
        }
    }

    @Nested
    @DisplayName("RefreshToken을 발급한다")
    class CreateRefreshToken {

        @Test
        void RT_생성_후_memberId를_추출한다() {
            // when
            final String token = tokenProvider.createRefreshToken(MEMBER_ID);

            // then
            final Long memberId = tokenProvider.extractMemberId(token);
            assertThat(memberId).isEqualTo(MEMBER_ID);
        }

        @Test
        void 발급된_RT는_유효하다() {
            // when
            final String token = tokenProvider.createRefreshToken(MEMBER_ID);

            // then
            assertThat(tokenProvider.isValid(token)).isTrue();
        }
    }

    @Nested
    @DisplayName("토큰의 유효성을 검증한다")
    class IsValid {

        @Test
        void 만료된_토큰은_유효하지_않다() {
            // given
            final Clock pastClock = Clock.fixed(
                TestClockConfig.NOW.minusSeconds(30 * 60), // 현재 시각보다 30분 전
                TestClockConfig.ZONE
            );
            final JwtTokenProvider pastProvider = new JwtTokenProvider(
                SECRET,
                15L,
                7L,
                pastClock
            );

            final String expiredToken = pastProvider.createAccessToken(MEMBER_ID);

            // when
            final boolean isValid = tokenProvider.isValid(expiredToken);

            // then
            assertThat(isValid).isFalse();
        }

        @Test
        void 변조된_토큰은_유효하지_않다() {
            // given
            final String token = tokenProvider.createAccessToken(MEMBER_ID);

            // when
            final String tampered = token + "tampered";

            // then
            assertThat(tokenProvider.isValid(tampered)).isFalse();
        }

        @Test
        void 다른_시크릿으로_서명된_토큰은_유효하지_않다() {
            // given
            final JwtTokenProvider otherProvider = new JwtTokenProvider(
                "other-secret-key-must-be-at-least-32-bytes!!",
                15L,
                7L,
                TestClockConfig.FIXED_CLOCK
            );
            final String token = otherProvider.createAccessToken(MEMBER_ID);

            // when
            final boolean isValid = tokenProvider.isValid(token);

            // then
            assertThat(isValid).isFalse();
        }
    }
}
