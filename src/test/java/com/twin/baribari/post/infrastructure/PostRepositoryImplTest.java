package com.twin.baribari.post.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.twin.baribari.fixture.PostFixture;
import com.twin.baribari.post.domain.Post;
import com.twin.baribari.post.domain.PostRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(PostRepositoryImpl.class)
class PostRepositoryImplTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void 게시물_정보를_저장한다() {
        // given
        final Post post = PostFixture.domain();

        // when
        final long savedId = postRepository.save(post);

        // then
        assertThat(postRepository.existsById(savedId)).isTrue();
    }

    @Nested
    @DisplayName("게시물 존재 여부를 아이디로 조회한다")
    class ExistsById {

        @Test
        void 저장된_게시물을_조회하면_TRUE를_반환한다() {
            // given
            final Post post = PostFixture.domain();
            final long savedId = postRepository.save(post);

            // when
            final boolean exists = postRepository.existsById(savedId);

            // then
            assertThat(exists).isTrue();
        }

        @Test
        void 존재하지_않는_게시물을_조회하면_FALSE를_반환한다() {
            // given
            final long unsavedId = 999L;

            // when
            final boolean exists = postRepository.existsById(unsavedId);

            // then
            assertThat(exists).isFalse();
        }
    }

    @Nested
    @DisplayName("게시물을 아이디로 조회한다")
    class GetById {

        @Test
        void 저장된_게시물_정보를_아이디로_조회한다() {
            // given
            final Post post = PostFixture.domain();
            final long savedId = postRepository.save(post);

            // when
            final Post found = postRepository.getById(savedId);

            // then
            assertThat(found.getTitle()).isEqualTo(post.getTitle());
            assertThat(found.getBody()).isEqualTo(post.getBody());
            assertThat(found.getMemberId()).isEqualTo(post.getMemberId());
            assertThat(found.getCourseId()).isEqualTo(post.getCourseId());
        }

        @Test
        void 존재하지_않는_게시물의_아이디로_조회하면_예외가_발생한다() {
            // given
            final long unsavedId = 999L;

            // when & then
            assertThatThrownBy(() -> postRepository.getById(unsavedId))
                .isInstanceOf(RuntimeException.class);
        }
    }

    @Nested
    @DisplayName("전체 게시물을 조회한다")
    class FindAll {

        @Test
        void 저장된_전체_게시물을_조회한다() {
            // given
            final Post post1 = PostFixture.domain("한강 코스", 1L, 2L);
            final Post post2 = PostFixture.domain("잠실 코스", 1L, 3L);
            postRepository.save(post1);
            postRepository.save(post2);

            // when
            final List<Post> posts = postRepository.findAll();

            // then
            assertThat(posts)
                .hasSize(2)
                .extracting(Post::getTitle)
                .containsExactlyInAnyOrder(post1.getTitle(), post2.getTitle());
        }
    }

    @Nested
    @DisplayName("게시물을 아이디로 삭제한다")
    class DeleteById {

        @Test
        void 저장된_게시물을_아이디로_삭제한다() {
            // given
            final Post post = PostFixture.domain();
            final long savedId = postRepository.save(post);

            // when
            postRepository.deleteById(savedId);

            // then
            assertThat(postRepository.existsById(savedId)).isFalse();
        }

        @Test
        void 존재하지_않는_게시물을_아이디로_삭제해도_예외가_발생하지_않는다() {
            // given
            final long unsavedId = 999L;

            // when & then
            assertThatCode(() -> postRepository.deleteById(unsavedId))
                .doesNotThrowAnyException();
        }
    }
}
