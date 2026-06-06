package com.twin.baribari.fixture;

import com.twin.baribari.post.domain.Post;
import com.twin.baribari.post.infrastructure.entity.PostJpaEntity;

public class PostFixture {

    public static Post domain() {
        return new Post("한강 라이딩 후기", "정말 좋았습니다.", 1L);
    }

    public static PostJpaEntity entity() {
        return PostJpaEntity.builder()
            .title("한강 라이딩 후기")
            .body("정말 좋았습니다.")
            .memberId(1L)
            .course(CourseFixture.entity())
            .build();
    }
}
