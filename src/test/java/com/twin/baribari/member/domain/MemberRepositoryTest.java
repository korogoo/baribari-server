package com.twin.baribari.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.twin.baribari.member.infrastructure.MemberRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(MemberRepositoryImpl.class)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Nested
    @DisplayName("회원 정보를 저장한다")
    class Save {

        @Test
        void 회원_정보를_저장한다() {
            // given
            Member member = new Member(
                "name",
                "baribari123@gmail.com",
                LoginProvider.GOOGLE,
                "socialId"
            );

            // when
            final long savedId = memberRepository.save(member);

            // then
            assertThat(savedId).isNotNull();
        }
    }
}
