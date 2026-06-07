package com.twin.baribari.fixture;

import com.twin.baribari.course.domain.Pin;

import com.twin.baribari.course.infrastructure.entity.CourseJpaEntity;
import com.twin.baribari.course.infrastructure.entity.PinJpaEntity;

public class PinFixture {

    public static Pin start() {
        return new Pin(
            37.5665,
            126.9780,
            0
        );
    }

    public static Pin waypoint(final int sequence) {
        return new Pin(
            37.5651,
            126.9895,
            sequence
        );
    }

    public static Pin end(final int sequence) {
        return new Pin(
            37.5700,
            126.9820,
            sequence
        );
    }

    public static PinJpaEntity startEntity(final CourseJpaEntity course) {
        return PinJpaEntity.builder()
            .latitude(37.5665)
            .longitude(126.9780)
            .sequence(0)
            .course(course)
            .build();
    }

    public static PinJpaEntity waypointEntity(final CourseJpaEntity course, final int sequence) {
        return PinJpaEntity.builder()
            .latitude(37.5651)
            .longitude(126.9895)
            .sequence(sequence)
            .course(course)
            .build();
    }

    public static PinJpaEntity endEntity(final CourseJpaEntity course, final int sequence) {
        return PinJpaEntity.builder()
            .latitude(37.5700)
            .longitude(126.9820)
            .sequence(sequence)
            .course(course)
            .build();
    }
}
