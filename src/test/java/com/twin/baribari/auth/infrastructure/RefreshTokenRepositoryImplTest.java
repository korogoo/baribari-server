package com.twin.baribari.auth.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.twin.baribari.auth.domain.RefreshToken;
import com.twin.baribari.auth.domain.RefreshTokenRepository;
import com.twin.baribari.auth.infrastructure.mapper.RefreshTokenMapper;
import com.twin.baribari.config.TestClockConfig;
import com.twin.baribari.fixture.RefreshTokenFixture;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({RefreshTokenRepositoryImpl.class, RefreshTokenMapper.class})
class RefreshTokenRepositoryImplTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Nested
    @DisplayName("RT를 저장한다")
    class Save {

        @Test
        void 신규_RT를_저장한다() {
            // given
            final RefreshToken domain = RefreshTokenFixture.domainForSave();

            // when
            final RefreshToken saved = refreshTokenRepository.save(domain);

            // then
            assertThat(saved.getId()).isNotNull();
            assertThat(saved.getMemberId()).isEqualTo(domain.getMemberId());
            assertThat(saved.getToken()).isEqualTo(domain.getToken());
            assertThat(saved.getExpiresAt()).isEqualTo(domain.getExpiresAt());
        }

        @Test
        void 이미_존재하는_회원이면_RT를_덮어쓴다() {
            // given
            final RefreshToken original = RefreshTokenFixture.domainForSave();
            refreshTokenRepository.save(original);

            final RefreshToken updated = new RefreshToken(
                original.getMemberId(),
                "new-refresh-token",
                LocalDateTime.now(TestClockConfig.FIXED_CLOCK).plusDays(7)
            );

            // when
            refreshTokenRepository.save(updated);

            // then
            final Optional<RefreshToken> stored = refreshTokenRepository.findByMemberId(original.getMemberId());
            assertThat(stored).isPresent();
            assertThat(stored.get().getToken()).isEqualTo("new-refresh-token");
        }
    }

    @Nested
    @DisplayName("memberId로 RT를 조회한다")
    class FindByMemberId {

        @Test
        void 저장된_RT가_있으면_반환한다() {
            // given
            final RefreshToken domain = RefreshTokenFixture.domainForSave();
            refreshTokenRepository.save(domain);

            // when
            final Optional<RefreshToken> result = refreshTokenRepository.findByMemberId(domain.getMemberId());

            // then
            assertThat(result).isPresent();
            assertThat(result.get().getToken()).isEqualTo(domain.getToken());
        }

        @Test
        void 저장된_RT가_없으면_빈값을_반환한다() {
            // when
            final Optional<RefreshToken> result = refreshTokenRepository.findByMemberId(999L);

            // then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("memberId로 RT를 삭제한다")
    class DeleteByMemberId {

        @Test
        void RT를_삭제한다() {
            // given
            final RefreshToken domain = RefreshTokenFixture.domainForSave();
            refreshTokenRepository.save(domain);

            // when
            refreshTokenRepository.deleteByMemberId(domain.getMemberId());

            // then
            assertThat(refreshTokenRepository.findByMemberId(domain.getMemberId())).isEmpty();
        }
    }
}
