package com.twin.baribari.course.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.twin.baribari.course.domain.Pin;
import com.twin.baribari.course.infrastructure.entity.PinJpaEntity;
import com.twin.baribari.fixture.PinFixture;
import org.junit.jupiter.api.Test;

class PinMapperTest {

    @Test
    void 핀_jpa엔티티를_도메인_엔티티로_변환한다() {
        // given
        final PinJpaEntity entity = PinFixture.startEntityWithCourseId(2L);

        // when
        final Pin domain = PinMapper.toDomain(entity);

        // then
        assertThat(domain.latitude()).isEqualTo(entity.getLatitude());
        assertThat(domain.longitude()).isEqualTo(entity.getLongitude());
        assertThat(domain.sequenceValue()).isEqualTo(entity.getSequence());
    }

    @Test
    void 핀_도메인_엔티티를_저장을_위한_jpa엔티티로_변환한다() {
        // given
        final Pin domain = PinFixture.start();

        // when
        final long courseId = 2L;
        final PinJpaEntity entity = PinMapper.toEntityForSave(domain, courseId);

        // then
        assertThat(entity.getId()).isNull();
        assertThat(entity.getLatitude()).isEqualTo(domain.latitude());
        assertThat(entity.getLongitude()).isEqualTo(domain.longitude());
        assertThat(entity.getSequence()).isEqualTo(domain.sequenceValue());
        assertThat(entity.getCourseId()).isEqualTo(courseId);
    }
}
