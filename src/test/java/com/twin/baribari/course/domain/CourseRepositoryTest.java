package com.twin.baribari.course.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import com.twin.baribari.course.infrastructure.CourseRepositoryImpl;
import com.twin.baribari.fixture.CourseFixture;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(CourseRepositoryImpl.class)
class CourseRepositoryTest {

    @Autowired
    CourseRepository courseRepository;

    @Test
    void 코스_정보를_저장한다() {
        // given
        final Course course = CourseFixture.domain();

        // when
        final long savedId = courseRepository.save(course);

        // then
        assertThat(courseRepository.existsById(savedId)).isTrue();
    }

    @Nested
    @DisplayName("코스 존재 여부를 아이디로 조회한다")
    class ExistsById {

        @Test
        void 저장된_코스를_조회하면_TRUE를_반환한다() {
            // given
            final Course course = CourseFixture.domain();
            final long savedId = courseRepository.save(course);

            // when
            final boolean exists = courseRepository.existsById(savedId);

            // then
            assertThat(exists).isTrue();
        }

        @Test
        void 존재하지_않는_코스를_조회하면_FALSE를_반환한다() {
            // given
            final long unsavedId = 999L;

            // when
            final boolean exists = courseRepository.existsById(unsavedId);

            // then
            assertThat(exists).isFalse();
        }
    }


    @Nested
    @DisplayName("코스를 아이디로 조회한다")
    class GetById {

        @Test
        void 저장된_코스_정보를_아이디로_조회한다() {
            // given
            final Course course = CourseFixture.domain();
            final long savedId = courseRepository.save(course);

            // when
            final Course found = courseRepository.getById(savedId);

            // then
            assertThat(found.getImageUrl()).isEqualTo(course.getImageUrl());
            assertThat(found.getTitle()).isEqualTo(course.getTitle());
            assertThat(found.getDescription()).isEqualTo(course.getDescription());
            assertThat(found.getPins())
                .hasSize(course.getPins().size())
                .extracting(Pin::latitude, Pin::longitude, Pin::sequenceValue)
                .containsExactly(
                    course.getPins().stream()
                        .map(pin ->
                            tuple(pin.latitude(), pin.longitude(), pin.sequenceValue()))
                        .toArray(Tuple[]::new)
                );
        }

        @Test
        void 존재하지_않는_코스의_아이디로_조회하면_예외가_발생한다() {
            // given
            final long unsavedId = 999L;

            // when & then
            assertThatThrownBy(() -> courseRepository.getById(unsavedId))
                .isInstanceOf(RuntimeException.class);
        }
    }

    @Nested
    @DisplayName("코스를 아이디로 삭제한다")
    class DeleteById {

        @Test
        void 저장된_코스를_아이디로_삭제한다() {
            // given
            final Course course = CourseFixture.domain();
            final long savedId = courseRepository.save(course);

            // when
            courseRepository.deleteById(savedId);

            // then
            assertThat(courseRepository.existsById(savedId)).isFalse();
        }

        @Test
        void 존재하지_않는_코스를_아이디로_삭제해도_예외가_발생하지_않는다() {
            // given
            final long unsavedId = 999L;

            // when & then
            assertThatCode(() -> courseRepository.deleteById(unsavedId))
                .doesNotThrowAnyException();
        }
    }
}
