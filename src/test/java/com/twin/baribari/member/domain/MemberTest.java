package com.twin.baribari.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Nested
    @DisplayName("객체를 생성한다")
    class Construction {

        @Test
        void 이름_이메일_로그인_제공자_소셜_아이디로_생성할_수_있다() {
            assertThatCode(() -> new Member("김바리", "baribari@gmail.com", LoginProvider.KAKAO, "kakao_123"))
                .doesNotThrowAnyException();
        }

        @Test
        void 이름이_NULL이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Member(null, "baribari@gmail.com", LoginProvider.KAKAO, "kakao_123"))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void 이메일이_NULL이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Member("김바리", null, LoginProvider.KAKAO, "kakao_123"))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void 로그인_제공자가_NULL이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Member("김바리", "baribari@gmail.com", null, "kakao_123"))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void 소셜_아이디가_NULL이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Member("김바리", "baribari@gmail.com", LoginProvider.KAKAO, null))
                .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("아이디로 동일한 객체임을 확인한다")
    class Equals {

        @Test
        void 아이디가_같으면_동일한_객체이다() {
            // given
            final Member member = new Member(1L, "김바리", "baribari@gmail.com", LoginProvider.KAKAO, "kakao_123");
            final Member other = new Member(1L, "이바리", "other@gmail.com", LoginProvider.GOOGLE, "google_456");

            // when
            final boolean equals = member.equals(other);

            // then
            assertThat(equals).isTrue();
        }

        @Test
        void 아이디가_다르면_다른_객체이다() {
            // given
            final Member member = new Member(1L, "김바리", "baribari@gmail.com", LoginProvider.KAKAO, "kakao_123");
            final Member other = new Member(2L, "김바리", "baribari@gmail.com", LoginProvider.KAKAO, "kakao_123");

            // when
            final boolean equals = member.equals(other);

            // then
            assertThat(equals).isFalse();
        }
    }
}
