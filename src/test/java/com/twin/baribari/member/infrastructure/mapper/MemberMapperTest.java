package com.twin.baribari.member.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.twin.baribari.fixture.MemberFixture;
import com.twin.baribari.member.domain.Member;
import com.twin.baribari.member.infrastructure.entity.MemberJpaEntity;
import org.junit.jupiter.api.Test;

class MemberMapperTest {

    private final MemberMapper memberMapper = new MemberMapper();

    @Test
    void 회원_jpa엔티티를_도메인_엔티티로_변환한다() {
        // given
        final MemberJpaEntity entity = MemberFixture.entity();

        // when
        final Member domain = memberMapper.toDomain(entity);

        // then
        assertThat(domain.getId()).isEqualTo(entity.getId());
        assertThat(domain.getName()).isEqualTo(entity.getName());
        assertThat(domain.getEmail()).isEqualTo(entity.getEmail());
        assertThat(domain.getLoginProvider()).isEqualTo(entity.getLoginProvider());
        assertThat(domain.getSocialId()).isEqualTo(entity.getSocialId());
    }

    @Test
    void 회원_도메인_엔티티를_저장을_위한_jpa엔티티로_변환한다() {
        // given
        final Member domain = MemberFixture.domainForSave();

        // when
        final MemberJpaEntity entity = memberMapper.toEntityForSave(domain);

        // then
        assertThat(entity.getId()).isNull();
        assertThat(entity.getName()).isEqualTo(domain.getName());
        assertThat(entity.getEmail()).isEqualTo(domain.getEmail());
        assertThat(entity.getLoginProvider()).isEqualTo(domain.getLoginProvider());
        assertThat(entity.getSocialId()).isEqualTo(domain.getSocialId());
    }
}
