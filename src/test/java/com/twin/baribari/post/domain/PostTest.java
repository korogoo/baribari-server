package com.twin.baribari.post.domain;

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
            assertThatCode(() -> new Post("제목", "내용", 1L))
                .doesNotThrowAnyException();
        }

        @Test
        void 게시물의_제목이_NULL이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Post(null, "내용", 1L))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void 게시물의_바디가_NULL이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Post("제목", null, 1L))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void 게시물_작성자_아이디가_0이하면_예외가_발생한다() {
            assertThatThrownBy(() -> new Post("제목", "내용", 0))
                .isInstanceOf(IllegalStateException.class);
        }
    }
}
