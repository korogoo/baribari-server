package com.twin.baribari.post.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Post {

    private final Long id;
    private final String title;
    private final String body;
    private final long memberId;
    private final long courseId;

    public Post(
        final Long id,
        final String title,
        final String body,
        final long memberId,
        final long courseId
    ) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.memberId = memberId;
        this.courseId = courseId;

        validateArguments();
    }

    public Post(
        final String title,
        final String body,
        final long memberId,
        final long courseId
    ) {
        this(null, title, body, memberId, courseId);
    }

    public Post update(final String newTitle, final String newBody) {
        return new Post(
            this.id,
            Objects.requireNonNullElse(newTitle, this.title),
            Objects.requireNonNullElse(newBody, this.body),
            this.memberId,
            this.courseId
        );
    }

    private void validateArguments() {
        Objects.requireNonNull(title, "게시물 title 은 필수값입니다.");

        if (memberId <= 0) {
            throw new IllegalStateException("게시물 작성자의 아이디는 0 이상이어야 합니다.");
        }
        if (courseId <= 0) {
            throw new IllegalStateException("게시물 코스의 아이디는 0 이상이어야 합니다.");
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Post post = (Post) other;
        return id != null && Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
