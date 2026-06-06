package com.twin.baribari.post.infrastructure.mapper;

import com.twin.baribari.post.domain.Post;
import com.twin.baribari.post.infrastructure.entity.PostJpaEntity;

public class PostMapper {

    public static Post toDomain(final PostJpaEntity entity) {
        return new Post(
            entity.getId(),
            entity.getTitle(),
            entity.getBody(),
            entity.getMemberId(),
            entity.getCourseId()
        );
    }

    public static PostJpaEntity toEntityForSave(final Post domain) {
        return PostJpaEntity.builder()
            .title(domain.getTitle())
            .body(domain.getBody())
            .memberId(domain.getMemberId())
            .courseId(domain.getCourseId())
            .build();
    }
}
