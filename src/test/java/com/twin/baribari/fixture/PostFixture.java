package com.twin.baribari.fixture;

import com.twin.baribari.course.presentation.dto.CreateCourseRequest;
import com.twin.baribari.course.presentation.dto.CreatePinRequest;
import com.twin.baribari.post.domain.Post;
import com.twin.baribari.post.infrastructure.entity.PostJpaEntity;
import com.twin.baribari.post.presentation.dto.CreatePostRequest;
import java.util.List;

public class PostFixture {

    public static CreatePostRequest createRequest() {
        return new CreatePostRequest(
            "한강 라이딩",
            "정말 좋았습니다.",
            new CreateCourseRequest(
                "https://image.url",
                "한강 코스",
                "한강을 따라 달리는 코스",
                List.of(
                    new CreatePinRequest(37.5, 127.0),
                    new CreatePinRequest(37.6, 127.1)
                )
            )
        );
    }

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
        return entity(2L);
    }

    public static PostJpaEntity entity(final long courseId) {
        return PostJpaEntity.builder()
            .title("한강 라이딩 후기")
            .body("정말 좋았습니다.")
            .memberId(3L)
            .courseId(courseId)
            .build();
    }
}
