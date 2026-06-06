package com.twin.baribari.fixture;

import com.twin.baribari.post.domain.Post;
import com.twin.baribari.post.infrastructure.entity.PostJpaEntity;

public class PostFixture {

    public static Post domain() {
        return new Post("한강 라이딩 후기", "정말 좋았습니다.", 2L, 5L);
    }

    public static Post domain(final long memberId, final long courseId) {
        return new Post("한강 라이딩 후기", "정말 좋았습니다.", memberId, courseId);
    }

    public static Post domain(final String title, final long memberId, final long courseId) {
        return new Post(title, "정말 좋았습니다.", memberId, courseId);
    }

    public static PostJpaEntity entity() {
        return PostJpaEntity.builder()
            .title("한강 라이딩 후기")
            .body("정말 좋았습니다.")
            .memberId(3L)
            .courseId(2L)
            .build();
    }
}
