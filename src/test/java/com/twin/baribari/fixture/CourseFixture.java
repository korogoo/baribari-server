package com.twin.baribari.fixture;

import com.twin.baribari.course.infrastructure.entity.CourseJpaEntity;

public class CourseFixture {

    public static CourseJpaEntity entity() {
        return CourseJpaEntity.builder()
            .id(null)
            .imageUrl("https://image.url/sample.jpg")
            .title("한강 라이딩 코스")
            .description("한강을 따라 달리는 코스입니다.")
            .build();
    }
}
