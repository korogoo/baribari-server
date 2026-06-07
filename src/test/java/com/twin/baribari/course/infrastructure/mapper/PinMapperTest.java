package com.twin.baribari.course.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.twin.baribari.course.domain.Pin;
import com.twin.baribari.course.infrastructure.entity.CourseJpaEntity;
import com.twin.baribari.course.infrastructure.entity.PinJpaEntity;
import com.twin.baribari.fixture.CourseFixture;
import com.twin.baribari.fixture.PinFixture;
import org.junit.jupiter.api.Test;

class PinMapperTest {

    @Test
    void 핀_jpa엔티티를_도메인_엔티티로_변환한다() {
        // given
        final PinJpaEntity entity = PinFixture.startEntity(CourseFixture.entity());

        // when
        final Pin domain = PinMapper.toDomain(entity);

        // then
        assertThat(domain.getLatitude()).isEqualTo(entity.getLatitude());
        assertThat(domain.getLongitude()).isEqualTo(entity.getLongitude());
        assertThat(domain.sequenceValue()).isEqualTo(entity.getSequence());
    }

    @Test
    void 핀_도메인_엔티티를_저장을_위한_jpa엔티티로_변환한다() {
        // given
        final Pin domain = PinFixture.start();
        final CourseJpaEntity courseEntity = CourseFixture.entity();

        // when
        final PinJpaEntity entity = PinMapper.toEntityForSave(domain, courseEntity);

        // then
        assertThat(entity.getId()).isNull();
        assertThat(entity.getLatitude()).isEqualTo(domain.getLatitude());
        assertThat(entity.getLongitude()).isEqualTo(domain.getLongitude());
        assertThat(entity.getSequence()).isEqualTo(domain.sequenceValue());
        assertThat(entity.getCourse().getId()).isEqualTo(courseEntity.getId());
    }
}
