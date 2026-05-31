package com.twin.baribari.member.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.twin.baribari.member.domain.LoginProvider;
import com.twin.baribari.member.domain.Member;
import com.twin.baribari.member.infrastructure.entity.MemberJpaEntity;
import org.junit.jupiter.api.Test;

class MemberMapperTest {

    @Test
    void 회원_jpa엔티티를_도메인_엔티티로_변환한다() {
        // given
        final MemberJpaEntity entity = new MemberJpaEntity(
            1L,
            "name",
            "baribari123@gmail.com",
            LoginProvider.GOOGLE,
            "socialId"
        );

        // when
        final Member domain = MemberMapper.toDomain(entity);

        // then
        assertThat(domain.getId()).isEqualTo(entity.getId());
        assertThat(domain.getName()).isEqualTo(entity.getName());
        assertThat(domain.getEmail()).isEqualTo(entity.getEmail());
        assertThat(domain.getLoginProvider()).isEqualTo(entity.getLoginProvider());
        assertThat(domain.getSocialId()).isEqualTo(entity.getSocialId());
    }

    @Test
    void 회원_도메인_엔티티를_jpa엔티티로_변환한다() {
        // given
        final Member domain = new Member(
            "name",
            "baribari123@gmail.com",
            LoginProvider.GOOGLE,
            "socialId"
        );

        // when
        final MemberJpaEntity entity = MemberMapper.toEntityForSave(domain);

        // then
        assertThat(entity.getName()).isEqualTo(domain.getName());
        assertThat(entity.getEmail()).isEqualTo(domain.getEmail());
        assertThat(entity.getLoginProvider()).isEqualTo(domain.getLoginProvider());
        assertThat(entity.getSocialId()).isEqualTo(domain.getSocialId());
    }

}
