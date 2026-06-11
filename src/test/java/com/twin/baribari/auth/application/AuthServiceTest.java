package com.twin.baribari.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.twin.baribari.auth.application.dto.TokenResponse;
import com.twin.baribari.auth.domain.TokenProvider;
import com.twin.baribari.auth.domain.exception.InvalidRefreshTokenException;
import com.twin.baribari.auth.domain.exception.RefreshTokenStolenException;
import com.twin.baribari.auth.infrastructure.RefreshTokenJpaRepository;
import com.twin.baribari.auth.infrastructure.RefreshTokenRepositoryImpl;
import com.twin.baribari.auth.infrastructure.entity.RefreshTokenJpaEntity;
import com.twin.baribari.auth.infrastructure.mapper.RefreshTokenMapper;
import com.twin.baribari.config.TestClockConfig;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@DataJpaTest
@Import({AuthService.class, RefreshTokenRepositoryImpl.class, RefreshTokenMapper.class, TestClockConfig.class})
@TestPropertySource(properties = "jwt.refresh-token-expiry-days=7")
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Autowired
    private Clock clock;

    @MockitoBean
    private TokenProvider tokenProvider;

    private static final Long MEMBER_ID = 1L;
    private static final String REFRESH_TOKEN = "refresh-token";
    private static final String NEW_ACCESS_TOKEN = "new-access-token";
    private static final String NEW_REFRESH_TOKEN = "new-refresh-token";

    @Nested
    @DisplayName("RefreshToken 을 발급한다")
    class IssueRefreshToken {

        @Test
        void RT_발급_시_DB에_저장된다() {
            // given
            given(tokenProvider.createRefreshToken(MEMBER_ID))
                .willReturn(REFRESH_TOKEN);

            // when
            authService.issueRefreshToken(MEMBER_ID);

            // then
            final Optional<RefreshTokenJpaEntity> saved = refreshTokenJpaRepository.findByMemberId(MEMBER_ID);
            assertThat(saved).isPresent();
            assertThat(saved.get().getToken()).isEqualTo(REFRESH_TOKEN);
        }
    }

    @Nested
    @DisplayName("AccessToken + RefreshToken 을 재발급한다 (Refresh Token Rotation)")
    class ReissueTokens {

        @Test
        void 유효한_RT면_새_AT와_RT를_반환한다() {
            // given
            given(tokenProvider.createRefreshToken(MEMBER_ID))
                .willReturn(REFRESH_TOKEN);
            authService.issueRefreshToken(MEMBER_ID);

            given(tokenProvider.extractMemberId(REFRESH_TOKEN))
                .willReturn(MEMBER_ID);
            given(tokenProvider.createAccessToken(MEMBER_ID))
                .willReturn(NEW_ACCESS_TOKEN);
            given(tokenProvider.createRefreshToken(MEMBER_ID))
                .willReturn(NEW_REFRESH_TOKEN);

            // when
            final TokenResponse response = authService.reissueTokens(REFRESH_TOKEN);

            // then
            assertThat(response.accessToken()).isEqualTo(NEW_ACCESS_TOKEN);
            assertThat(response.refreshToken()).isEqualTo(NEW_REFRESH_TOKEN);
        }

        @Test
        void RTR_재발급_후_DB의_RT가_교체된다() {
            // given
            given(tokenProvider.createRefreshToken(MEMBER_ID))
                .willReturn(REFRESH_TOKEN);
            authService.issueRefreshToken(MEMBER_ID);

            given(tokenProvider.extractMemberId(REFRESH_TOKEN))
                .willReturn(MEMBER_ID);
            given(tokenProvider.createAccessToken(MEMBER_ID))
                .willReturn(NEW_ACCESS_TOKEN);
            given(tokenProvider.createRefreshToken(MEMBER_ID))
                .willReturn(NEW_REFRESH_TOKEN);

            // when
            authService.reissueTokens(REFRESH_TOKEN);

            // then
            final Optional<RefreshTokenJpaEntity> stored = refreshTokenJpaRepository.findByMemberId(MEMBER_ID);
            assertThat(stored.get().getToken()).isEqualTo(NEW_REFRESH_TOKEN);
        }

        @Test
        void 탈취된_RT면_DB_RT를_삭제하고_예외를_던진다() {
            // given
            given(tokenProvider.createRefreshToken(MEMBER_ID))
                .willReturn(REFRESH_TOKEN);
            authService.issueRefreshToken(MEMBER_ID);

            given(tokenProvider.extractMemberId(any()))
                .willReturn(MEMBER_ID);

            // when & then
            assertThatThrownBy(() -> authService.reissueTokens("stolen-token"))
                .isInstanceOf(RefreshTokenStolenException.class);

            assertThat(refreshTokenJpaRepository.findByMemberId(MEMBER_ID)).isEmpty();
        }

        @Test
        void 만료된_RT면_예외를_던진다() {
            // given
            final Clock pastClock = Clock.fixed(
                TestClockConfig.NOW.minusSeconds(7 * 24 * 60 * 60 + 1), // 만료일자 + 1초
                TestClockConfig.ZONE
            );
            refreshTokenJpaRepository.save(
                RefreshTokenJpaEntity.builder()
                    .memberId(MEMBER_ID)
                    .token(REFRESH_TOKEN)
                    .expiresAt(LocalDateTime.now(pastClock).plusDays(7))
                    .build()
            );

            given(tokenProvider.extractMemberId(REFRESH_TOKEN))
                .willReturn(MEMBER_ID);

            // when & then
            assertThatThrownBy(() -> authService.reissueTokens(REFRESH_TOKEN))
                .isInstanceOf(InvalidRefreshTokenException.class);
        }

        @Test
        void DB에_RT가_없으면_예외를_던진다() {
            // given
            given(tokenProvider.extractMemberId(REFRESH_TOKEN))
                .willReturn(MEMBER_ID);

            // when & then
            assertThatThrownBy(() -> authService.reissueTokens(REFRESH_TOKEN))
                .isInstanceOf(InvalidRefreshTokenException.class);
        }
    }

    @Nested
    @DisplayName("로그아웃한다")
    class RevokeToken {

        @Test
        void 로그아웃_시_DB에서_RT가_삭제된다() {
            // given
            given(tokenProvider.createRefreshToken(MEMBER_ID))
                .willReturn(REFRESH_TOKEN);
            authService.issueRefreshToken(MEMBER_ID);

            // when
            authService.revokeToken(MEMBER_ID);

            // then
            assertThat(refreshTokenJpaRepository.findByMemberId(MEMBER_ID)).isEmpty();
        }
    }
}
