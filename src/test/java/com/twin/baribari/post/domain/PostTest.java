package com.twin.baribari.post.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PostTest {

    @Nested
    @DisplayName("객체를 생성한다")
    class Construction {

        @Test
        void 게시물_제목_내용_회원_아이디로_생성할_수_있다() {
            assertThatCode(() -> new Post("제목", "내용", 2L, 3L))
                .doesNotThrowAnyException();
        }

        @Test
        void 게시물의_제목이_NULL이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Post(null, "내용", 2L, 3L))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void 게시물의_바디가_NULL이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Post("제목", null, 2L, 3L))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void 게시물_작성자_아이디가_0이하면_예외가_발생한다() {
            assertThatThrownBy(() -> new Post("제목", "내용", 0L, 2L))
                .isInstanceOf(IllegalStateException.class);
        }

        @Test
        void 게시물_코스_아이디가_0이하면_예외가_발생한다() {
            assertThatThrownBy(() -> new Post("제목", "내용", 2L, 0L))
                .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    @DisplayName("아이디로 동일한 객체임을 확인한다")
    class Equals {

        @Test
        void 아이디가_같으면_동일한_객체이다() {
            // given
            final Post post = new Post(1L, "제목", "내용", 2L, 3L);
            final Post other = new Post(1L, "다른 제목", "다른 내용", 4L, 5L);

            // when
            final boolean equals = post.equals(other);

            // then
            assertThat(equals).isTrue();
        }

        @Test
        void 아이디가_다르면_다른_객체이다() {
            // given
            final Post post = new Post(1L, "제목", "내용", 2L, 3L);
            final Post other = new Post(2L, "제목", "내용", 2L, 3L);

            // when
            final boolean equals = post.equals(other);

            // then
            assertThat(equals).isFalse();
        }
    }
}
