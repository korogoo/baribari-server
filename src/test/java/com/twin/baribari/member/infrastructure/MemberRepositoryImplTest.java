package com.twin.baribari.member.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.twin.baribari.fixture.MemberFixture;
import com.twin.baribari.member.domain.Member;
import com.twin.baribari.member.domain.MemberRepository;
import com.twin.baribari.member.infrastructure.mapper.MemberMapper;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({MemberRepositoryImpl.class, MemberMapper.class})
class MemberRepositoryImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("회원을 저장한다")
    class Save {

        @Test
        void 신규_회원을_저장한다() {
            // given
            final Member domain = MemberFixture.domainForSave();

            // when
            final Member saved = memberRepository.save(domain);

            // then
            assertThat(saved.getId()).isNotNull();
            assertThat(saved.getName()).isEqualTo(domain.getName());
            assertThat(saved.getEmail()).isEqualTo(domain.getEmail());
            assertThat(saved.getLoginProvider()).isEqualTo(domain.getLoginProvider());
            assertThat(saved.getSocialId()).isEqualTo(domain.getSocialId());
        }
    }

    @Nested
    @DisplayName("socialId로 회원을 조회한다")
    class FindBySocialId {

        @Test
        void 저장된_회원이_있으면_반환한다() {
            // given
            final Member domain = MemberFixture.domainForSave();
            memberRepository.save(domain);

            // when
            final Optional<Member> result = memberRepository.findBySocialId(domain.getSocialId());

            // then
            assertThat(result).isPresent();
            assertThat(result.get().getSocialId()).isEqualTo(domain.getSocialId());
            assertThat(result.get().getName()).isEqualTo(domain.getName());
        }

        @Test
        void 저장된_회원이_없으면_빈값을_반환한다() {
            // when
            final Optional<Member> result = memberRepository.findBySocialId("존재하지_않는_socialId");

            // then
            assertThat(result).isEmpty();
        }
    }
}
