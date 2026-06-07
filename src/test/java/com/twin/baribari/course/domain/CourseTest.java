package com.twin.baribari.course.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import com.twin.baribari.fixture.PinsFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CourseTest {

    @Nested
    @DisplayName("객체를 생성한다")
    class Construction {

        @Test
        void 코스_정보로_생성할_수_있다() {
            assertThatCode(() -> new Course("https://image.url", "한강 코스", "한강을 달리는 코스", PinsFixture.pins()))
                .doesNotThrowAnyException();
        }

        @Test
        void 이미지_주소가_NULL이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Course(null, "한강 코스", "한강을 달리는 코스", PinsFixture.pins()))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void 제목이_NULL이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Course("https://image.url", null, "한강을 달리는 코스", PinsFixture.pins()))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void 설명이_NULL이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Course("https://image.url", "한강 코스", null, PinsFixture.pins()))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        void 핀_리스트가_NULL이면_예외가_발생한다() {
            assertThatThrownBy(() -> new Course("https://image.url", "한강 코스", "한강을 달리는 코스", null))
                .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("아이디로 동일한 객체임을 조회한다")
    class Equals {

        @Test
        void 아이디가_같으면_동일한_객체이다() {
            // given
            final Course post = new Course(1L, "url", "title", "description", PinsFixture.pins());
            final Course other = new Course(1L, "other", "other", "other", PinsFixture.pinsWithWaypoint());

            // when
            final boolean equals = post.equals(other);

            // then
            assertThat(equals).isTrue();
        }

        @Test
        void 아이디가_다르면_다른_객체이다() {
            // given
            final Course post = new Course(1L, "url", "title", "description", PinsFixture.pins());
            final Course other = new Course(2L, "url", "title", "description", PinsFixture.pins());

            // when
            final boolean equals = post.equals(other);

            // then
            assertThat(equals).isFalse();
        }

        @Test
        void 아이디가_null_이면_다른_객체이다() {
            // given
            final Course post = new Course("url", "title", "description", PinsFixture.pins());
            final Course other = new Course("url", "title", "description", PinsFixture.pins());

            // when
            final boolean equals = post.equals(other);

            // then
            assertThat(equals).isFalse();
        }
    }

    @Test
    void 모든_핀을_조회한다() {
        // given
        final Pins pins = PinsFixture.pins();
        final Course course = new Course(1L, "url", "title", "description", pins);

        // when
        final List<Pin> actual = course.getPins();

        // then
        final List<Pin> expected = pins.toList();
        assertThat(actual)
            .extracting(Pin::getLatitude, Pin::getLongitude, Pin::sequenceValue)
            .containsExactlyElementsOf(
                expected.stream()
                    .map(p -> tuple(p.getLatitude(), p.getLongitude(), p.sequenceValue()))
                    .toList()
            );
    }
}
