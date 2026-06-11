package com.twin.baribari.auth.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.twin.baribari.auth.domain.RefreshToken;
import com.twin.baribari.auth.infrastructure.entity.RefreshTokenJpaEntity;
import com.twin.baribari.fixture.RefreshTokenFixture;
import org.junit.jupiter.api.Test;

class RefreshTokenMapperTest {

    private final RefreshTokenMapper mapper = new RefreshTokenMapper();

    @Test
    void jpa엔티티를_도메인으로_변환한다() {
        // given
        final RefreshTokenJpaEntity entity = RefreshTokenFixture.entity();

        // when
        final RefreshToken domain = mapper.toDomain(entity);

        // then
        assertThat(domain.getId()).isEqualTo(entity.getId());
        assertThat(domain.getMemberId()).isEqualTo(entity.getMemberId());
        assertThat(domain.getToken()).isEqualTo(entity.getToken());
        assertThat(domain.getExpiresAt()).isEqualTo(entity.getExpiresAt());
    }

    @Test
    void 도메인을_저장용_jpa엔티티로_변환한다() {
        // given
        final RefreshToken domain = RefreshTokenFixture.domainForSave();

        // when
        final RefreshTokenJpaEntity entity = mapper.toEntityForSave(domain);

        // then
        assertThat(entity.getId()).isNull();
        assertThat(entity.getMemberId()).isEqualTo(domain.getMemberId());
        assertThat(entity.getToken()).isEqualTo(domain.getToken());
        assertThat(entity.getExpiresAt()).isEqualTo(domain.getExpiresAt());
    }
}
