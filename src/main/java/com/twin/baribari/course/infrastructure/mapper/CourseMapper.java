package com.twin.baribari.course.infrastructure.mapper;

import com.twin.baribari.course.domain.Course;
import com.twin.baribari.course.domain.Pins;
import com.twin.baribari.course.infrastructure.entity.CourseJpaEntity;

public class CourseMapper {

    public static Course toDomain(final CourseJpaEntity entity) {
        return new Course(
            entity.getImageUrl(),
            entity.getTitle(),
            entity.getDescription(),
            new Pins(entity.getPins().stream()
                .map(PinMapper::toDomain)
                .toList()
            ));
    }

    public static CourseJpaEntity toEntityForSave(final Course domain) {
        final CourseJpaEntity courseJpaEntity = CourseJpaEntity.builder()
            .imageUrl(domain.getImageUrl())
            .title(domain.getTitle())
            .description(domain.getDescription())
            .build();

        courseJpaEntity.addPins(domain.getPins().stream()
            .map(pin -> PinMapper.toEntityForSave(pin, courseJpaEntity))
            .toList()
        );

        return courseJpaEntity;
    }
}
