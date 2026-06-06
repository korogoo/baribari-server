package com.twin.baribari.post.application.dto;

import com.twin.baribari.post.domain.Post;

public record PostDetailResponse(
    long id,
    String title,
    String body,
    long memberId,
    long courseId
) {

    public static PostDetailResponse from(final Post post) {
        return new PostDetailResponse(post.getId(), post.getTitle(), post.getBody(), post.getMemberId(),
            post.getCourseId());
    }
}
