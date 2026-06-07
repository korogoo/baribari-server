package com.twin.baribari.course.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.twin.baribari.course.domain.Course;
import com.twin.baribari.course.domain.Pin;
import com.twin.baribari.course.infrastructure.entity.CourseJpaEntity;
import com.twin.baribari.course.infrastructure.entity.PinJpaEntity;
import com.twin.baribari.fixture.CourseFixture;
import com.twin.baribari.fixture.PostFixture;
import com.twin.baribari.post.domain.Post;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CourseMapperTest {

    @Test
    void 코스_jpa엔티티를_도메인_엔티티로_변환한다() {
        // given
        final CourseJpaEntity entity = CourseFixture.entityWithPins();

        // when
        final Course domain = CourseMapper.toDomain(entity);

        // then
        assertThat(domain.getId()).isEqualTo(entity.getId());
        assertThat(domain.getImageUrl()).isEqualTo(entity.getImageUrl());
        assertThat(domain.getTitle()).isEqualTo(entity.getTitle());
        assertThat(domain.getDescription()).isEqualTo(entity.getDescription());
        assertThat(domain.getPins())
            .hasSize(entity.getPins().size())
            .extracting(Pin::getLatitude, Pin::getLongitude, Pin::sequenceValue)
            .containsExactly(
                entity.getPins().stream()
                    .map(pinJpaEntity ->
                        tuple(pinJpaEntity.getLatitude(), pinJpaEntity.getLongitude(), pinJpaEntity.getSequence()))
                    .toArray(Tuple[]::new)
            );
    }

    @Test
    void 코스_도메인_엔티티를_저장을_위한_jpa엔티티로_변환한다() {
        // given
        final Course domain = CourseFixture.domain();

        // when
        final CourseJpaEntity entity = CourseMapper.toEntityForSave(domain);

        // then
        assertThat(entity.getId()).isNull();
        assertThat(entity.getImageUrl()).isEqualTo(domain.getImageUrl());
        assertThat(entity.getTitle()).isEqualTo(domain.getTitle());
        assertThat(entity.getDescription()).isEqualTo(domain.getDescription());
        assertThat(entity.getPins())
            .hasSize(domain.getPins().size())
            .extracting(PinJpaEntity::getLatitude, PinJpaEntity::getLongitude, PinJpaEntity::getSequence)
            .containsExactly(
                domain.getPins().stream()
                    .map(pin ->
                        tuple(pin.getLatitude(), pin.getLongitude(), pin.sequenceValue()))
                    .toArray(Tuple[]::new)
            );
    }

    @Nested
    @DisplayName("아이디로 동일한 객체임을 조회한다")
    class Equals {

        @Test
        void 아이디가_같으면_동일한_객체이다() {
            // given
            final Post post = new Post(1L, "title", "body", 1L, 1L);
            final Post other = new Post(1L, "other", "other", 2L, 2L);

            // when
            final boolean equals = post.equals(other);

            // then
            assertThat(equals).isTrue();
        }

        @Test
        void 아이디가_다르면_다른_객체이다() {
            // given
            final Post post = new Post(1L, "title", "body", 1L, 2L);
            final Post other = new Post(2L, "title", "body", 1L, 2L);

            // when
            final boolean equals = post.equals(other);

            // then
            assertThat(equals).isFalse();
        }
    }
}
