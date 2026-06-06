package com.twin.baribari.post.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.twin.baribari.fixture.PostFixture;
import com.twin.baribari.post.domain.Post;
import com.twin.baribari.post.infrastructure.entity.PostJpaEntity;
import org.junit.jupiter.api.Test;

class PostMapperTest {

    @Test
    void 게시물_jpa엔티티를_도메인_엔티티로_변환한다() {
        // given
        final PostJpaEntity entity = PostFixture.entity();

        // when
        final Post domain = PostMapper.toDomain(entity);

        // then
        assertThat(domain.getTitle()).isEqualTo(entity.getTitle());
        assertThat(domain.getBody()).isEqualTo(entity.getBody());
        assertThat(domain.getMemberId()).isEqualTo(entity.getMemberId());
        assertThat(domain.getCourseId()).isEqualTo(entity.getCourseId());
    }

    @Test
    void 게시물_도메인_엔티티를_저장을_위한_jpa엔티티로_변환한다() {
        // given
        final Post domain = PostFixture.domain();

        // when
        final PostJpaEntity entity = PostMapper.toEntityForSave(domain);

        // then
        assertThat(entity.getId()).isNull();
        assertThat(entity.getTitle()).isEqualTo(domain.getTitle());
        assertThat(entity.getBody()).isEqualTo(domain.getBody());
        assertThat(entity.getMemberId()).isEqualTo(domain.getMemberId());
        assertThat(entity.getCourseId()).isEqualTo(domain.getCourseId());
    }
}
