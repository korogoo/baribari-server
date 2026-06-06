package com.twin.baribari.fixture;

import com.twin.baribari.member.domain.LoginProvider;
import com.twin.baribari.member.domain.Member;
import com.twin.baribari.member.infrastructure.entity.MemberJpaEntity;

public class MemberFixture {

    public static Member domain() {
        return new Member("김바리", "baribari@gmail.com", LoginProvider.KAKAO, "kakao_123");
    }

    public static MemberJpaEntity entity() {
        return MemberJpaEntity.builder()
            .name("김바리")
            .email("baribari@gmail.com")
            .loginProvider(LoginProvider.KAKAO)
            .socialId("kakao_123")
            .build();
    }
}
