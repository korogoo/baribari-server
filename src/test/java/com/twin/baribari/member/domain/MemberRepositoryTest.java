package com.twin.baribari.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.twin.baribari.member.infrastructure.MemberRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(MemberRepositoryImpl.class)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원_정보를_저장한다() {
        // given
        final Member member = new Member(
            "name",
            "baribari123@gmail.com",
            LoginProvider.GOOGLE,
            "socialId"
        );

        // when
        final Member saved = memberRepository.save(member);

        // then
        assertThat(saved.getId()).isPositive();
    }
}
