package com.twin.baribari.fixture;

import com.twin.baribari.course.domain.Course;
import com.twin.baribari.course.domain.Pins;
import com.twin.baribari.course.infrastructure.entity.CourseJpaEntity;
import com.twin.baribari.course.infrastructure.entity.PinJpaEntity;
import java.util.List;

public class CourseFixture {

    public static CourseJpaEntity entity() {
        return CourseJpaEntity.builder()
            .id(1L)
            .imageUrl("https://image.url/sample.jpg")
            .title("한강 라이딩 코스")
            .description("한강을 따라 달리는 코스입니다.")
            .build();
    }

    public static CourseJpaEntity entityWithPins() {
        final CourseJpaEntity course = entity();
        final List<PinJpaEntity> pins = List.of(
            PinFixture.startEntity(course),
            PinFixture.waypointEntity(course, 1),
            PinFixture.endEntity(course, 2)
        );
        course.addPins(pins);
        return course;
    }

    public static Course domain() {
        return new Course(
            "https://image.url/sample.jpg",
            "한강 라이딩 코스",
            "한강을 따라 달리는 코스입니다.",
            new Pins(List.of(
                PinFixture.start(),
                PinFixture.waypoint(1),
                PinFixture.end(2)
            ))
        );
    }
}
