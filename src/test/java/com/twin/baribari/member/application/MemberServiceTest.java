package com.twin.baribari.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.twin.baribari.fixture.MemberFixture;
import com.twin.baribari.member.domain.Member;
import com.twin.baribari.member.domain.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("소셜 로그인 식별값(socialId)으로 회원을 조회하거나 생성한다")
    class FindOrCreate {

        @Test
        void 이미_가입된_회원이면_기존_회원을_반환한다() {
            // given
            final Member existing = MemberFixture.domain(1L);
            given(memberRepository.findBySocialId(existing.getSocialId()))
                .willReturn(Optional.of(existing));

            // when
            final Member result = memberService.findBySocialIdOrRegister(
                existing.getSocialId(),
                existing.getName(),
                existing.getEmail(),
                existing.getLoginProvider()
            );

            // then
            assertThat(result.getId()).isEqualTo(existing.getId());

            verify(memberRepository, never()).save(any());
        }

        @Test
        void 처음_로그인한_회원이면_신규_생성_후_반환한다() {
            // given
            final Member created = MemberFixture.domain(2L);
            given(memberRepository.findBySocialId(created.getSocialId()))
                .willReturn(Optional.empty());
            given(memberRepository.save(any()))
                .willReturn(created);

            // when
            final Member result = memberService.findBySocialIdOrRegister(
                created.getSocialId(),
                created.getName(),
                created.getEmail(),
                created.getLoginProvider()
            );

            // then
            assertThat(result.getId()).isEqualTo(created.getId());

            final ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
            verify(memberRepository).save(captor.capture());

            final Member saved = captor.getValue();
            assertThat(saved.getSocialId()).isEqualTo(created.getSocialId());
            assertThat(saved.getName()).isEqualTo(created.getName());
            assertThat(saved.getEmail()).isEqualTo(created.getEmail());
            assertThat(saved.getLoginProvider()).isEqualTo(created.getLoginProvider());
        }
    }
}
