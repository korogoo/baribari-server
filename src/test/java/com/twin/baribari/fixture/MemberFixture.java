package com.twin.baribari.fixture;

import com.twin.baribari.member.domain.LoginProvider;
import com.twin.baribari.member.domain.Member;
import com.twin.baribari.member.infrastructure.entity.MemberJpaEntity;

public class MemberFixture {

    public static Member domainForSave() {
        return new Member("김바리", "baribari@gmail.com", LoginProvider.KAKAO, "kakao_123");
    }

    public static Member domain(final long id) {
        return new Member(id, "김바리", "baribari@gmail.com", LoginProvider.KAKAO, "kakao_123");
    }

    public static MemberJpaEntity entity() {
        return MemberJpaEntity.builder()
            .id(1L)
            .name("김바리")
            .email("baribari@gmail.com")
            .loginProvider(LoginProvider.KAKAO)
            .socialId("kakao_123")
            .build();
    }
}
