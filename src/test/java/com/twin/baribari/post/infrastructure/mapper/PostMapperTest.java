package com.twin.baribari.post.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.twin.baribari.course.infrastructure.entity.CourseJpaEntity;
import com.twin.baribari.course.infrastructure.entity.PinJpaEntity;
import com.twin.baribari.fixture.CourseFixture;
import com.twin.baribari.fixture.PostFixture;
import com.twin.baribari.post.domain.Post;
import com.twin.baribari.post.infrastructure.entity.PostJpaEntity;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

class PostMapperTest {

    @Test
    void 게시물_jpa엔티티를_도메인_엔티티로_변환한다() {
        // given
        final PostJpaEntity entity = PostFixture.entity();

        // when
        final Post domain = PostMapper.toDomain(entity);

        // then
        assertThat(domain.getTitle()).isEqualTo(entity.getTitle());
        assertThat(domain.getBody()).isEqualTo(entity.getBody());
        assertThat(domain.getMemberId()).isEqualTo(entity.getMemberId());
    }

    @Test
    void 게시물_도메인_엔티티를_저장을_위한_jpa엔티티로_변환한다() {
        // given
        final Post domain = PostFixture.domain();
        final CourseJpaEntity courseEntity = CourseFixture.entityWithPins();

        // when
        final PostJpaEntity entity = PostMapper.toEntityForSave(domain, courseEntity);

        // then
        assertThat(entity.getId()).isNull();
        assertThat(entity.getTitle()).isEqualTo(domain.getTitle());
        assertThat(entity.getBody()).isEqualTo(domain.getBody());
        assertThat(entity.getMemberId()).isEqualTo(domain.getMemberId());
        assertThat(entity.getCourse().getId()).isEqualTo(courseEntity.getId());
        assertThat(entity.getCourse().getImageUrl()).isEqualTo(courseEntity.getImageUrl());
        assertThat(entity.getCourse().getTitle()).isEqualTo(courseEntity.getTitle());
        assertThat(entity.getCourse().getDescription()).isEqualTo(courseEntity.getDescription());
        assertThat(entity.getCourse().getPins())
            .hasSize(courseEntity.getPins().size())
            .extracting(PinJpaEntity::getLatitude, PinJpaEntity::getLongitude, PinJpaEntity::getSequence)
            .containsExactly(
                courseEntity.getPins().stream()
                    .map(pinJpaEntity ->
                        tuple(pinJpaEntity.getLatitude(), pinJpaEntity.getLongitude(), pinJpaEntity.getSequence()))
                    .toArray(Tuple[]::new)
            );
    }
}
