package com.twin.baribari.course.infrastructure.mapper;

import com.twin.baribari.course.domain.Pin;
import com.twin.baribari.course.domain.Sequence;
import com.twin.baribari.course.infrastructure.entity.PinJpaEntity;

public class PinMapper {

    public static Pin toDomain(final PinJpaEntity entity) {
        return new Pin(
            entity.getLatitude(),
            entity.getLongitude(),
            new Sequence(entity.getSequence())
        );
    }

    public static PinJpaEntity toEntityForSave(final Pin domain, final long courseId) {
        return new PinJpaEntity(
            null,
            domain.latitude(),
            domain.longitude(),
            domain.sequenceValue(),
            courseId
        );
    }
}
