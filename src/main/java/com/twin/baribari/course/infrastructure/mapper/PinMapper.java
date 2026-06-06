package com.twin.baribari.course.infrastructure.mapper;

import com.twin.baribari.course.domain.Pin;
import com.twin.baribari.course.domain.Sequence;
import com.twin.baribari.course.infrastructure.entity.CourseJpaEntity;
import com.twin.baribari.course.infrastructure.entity.PinJpaEntity;

public class PinMapper {

    public static Pin toDomain(final PinJpaEntity entity) {
        return new Pin(
            entity.getLatitude(),
            entity.getLongitude(),
            new Sequence(entity.getSequence())
        );
    }

    public static PinJpaEntity toEntityForSave(final Pin domain, final CourseJpaEntity courseJpaEntity) {
        return PinJpaEntity.builder()
            .latitude(domain.latitude())
            .longitude(domain.longitude())
            .sequence(domain.sequenceValue())
            .course(courseJpaEntity)
            .build();
    }
}
