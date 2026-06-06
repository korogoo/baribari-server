package com.twin.baribari.post.application.dto;

import com.twin.baribari.post.domain.Post;

public record PostSummaryResponse(
    long id,
    String title,
    long memberId,
    long courseId
) {

    public static PostSummaryResponse from(final Post post) {
        return new PostSummaryResponse(
            post.getId(),
            post.getTitle(),
            post.getMemberId(),
            post.getCourseId()
        );
    }
}
