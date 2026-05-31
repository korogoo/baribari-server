package com.twin.baribari.fixture;

import com.twin.baribari.course.domain.Pin;
import com.twin.baribari.course.domain.Sequence;
import com.twin.baribari.course.infrastructure.entity.PinJpaEntity;

public class PinFixture {

    public static Pin start() {
        return new Pin(
            37.5665,
            126.9780,
            new Sequence(0)
        );
    }

    public static Pin waypoint(final int sequence) {
        return new Pin(
            37.5651,
            126.9895,
            new Sequence(sequence)
        );
    }

    public static Pin end(final int sequence) {
        return new Pin(
            37.5700,
            126.9820,
            new Sequence(sequence)
        );
    }

    public static PinJpaEntity startEntityWithCourseId(final long courseId) {
        return new PinJpaEntity(
            1L,
            37.5665,
            126.9780,
            1,
            courseId
        );
    }

    public static PinJpaEntity waypointEntityWithCourseId(final long courseId, final int sequence) {
        return new PinJpaEntity(
            2L,
            37.5651,
            126.9895,
            sequence,
            courseId
        );
    }

    public static PinJpaEntity endEntityWithCourseId(final long courseId, final int sequence) {
        return new PinJpaEntity(
            3L,
            37.5700,
            126.9820,
            sequence,
            courseId
        );
    }
}
