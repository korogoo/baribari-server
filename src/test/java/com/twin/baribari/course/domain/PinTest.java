package com.twin.baribari.course.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PinTest {

    @Nested
    @DisplayName("객체를 생성한다")
    class Construction {

        @Test
        void 위도_경도_순서로_생성할_수_있다() {
            assertThatCode(() -> new Pin(37.5665, 126.9780, 0))
                .doesNotThrowAnyException();
        }

        @Test
        void 위도가_최솟값보다_작으면_예외가_발생한다() {
            assertThatThrownBy(() -> new Pin(-90.1, 126.9780, 0))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 위도가_최댓값보다_크면_예외가_발생한다() {
            assertThatThrownBy(() -> new Pin(90.1, 126.9780, 0))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 경도가_최솟값보다_작으면_예외가_발생한다() {
            assertThatThrownBy(() -> new Pin(37.5665, -180.1, 0))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 경도가_최댓값보다_크면_예외가_발생한다() {
            assertThatThrownBy(() -> new Pin(37.5665, 180.1, 0))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 순서가_음수이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Pin(37.5665, 126.9780, -1))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("아이디로 동일한 객체임을 확인한다")
    class Equals {

        @Test
        void 아이디가_같으면_동일한_객체이다() {
            // given
            final Pin pin = new Pin(1L, 37.5665, 126.9780, 0);
            final Pin other = new Pin(1L, 37.5700, 126.9820, 1);

            // when
            final boolean equals = pin.equals(other);

            // then
            assertThat(equals).isTrue();
        }

        @Test
        void 아이디가_다르면_다른_객체이다() {
            // given
            final Pin pin = new Pin(1L, 37.5665, 126.9780, 0);
            final Pin other = new Pin(2L, 37.5665, 126.9780, 0);

            // when
            final boolean equals = pin.equals(other);

            // then
            assertThat(equals).isFalse();
        }

        @Test
        void 아이디가_null_이면_다른_객체이다() {
            // given
            final Pin pin = new Pin(37.5665, 126.9780, 0);
            final Pin other = new Pin(37.5665, 126.9780, 0);

            // when
            final boolean equals = pin.equals(other);

            // then
            assertThat(equals).isFalse();
        }
    }
}
