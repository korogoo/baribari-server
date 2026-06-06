package com.twin.baribari.post.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Post {

    private final String title;
    private final String body;
    private final long memberId;
    private final long courseId;

    public Post(
        final String title,
        final String body,
        final long memberId,
        final long courseId
    ) {
        this.title = title;
        this.body = body;
        this.memberId = memberId;
        this.courseId = courseId;

        validateArguments();
    }

    private void validateArguments() {
        Objects.requireNonNull(title, "게시물 title 은 필수값입니다.");
        Objects.requireNonNull(body, "게시물 body 는 필수값입니다.");

        if (memberId <= 0) {
            throw new IllegalStateException("게시물 작성자의 아이디는 0 이상이어야 합니다.");
        }
        if (courseId <= 0) {
            throw new IllegalStateException("게시물 코스의 아이디는 0 이상이어야 합니다.");
        }
    }
}
