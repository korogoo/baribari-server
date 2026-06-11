package com.twin.baribari.post.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twin.baribari.course.infrastructure.CourseJpaRepository;
import com.twin.baribari.course.infrastructure.entity.CourseJpaEntity;
import com.twin.baribari.fixture.CourseFixture;
import com.twin.baribari.fixture.PostFixture;
import com.twin.baribari.post.infrastructure.PostJpaRepository;
import com.twin.baribari.post.infrastructure.entity.PostJpaEntity;
import com.twin.baribari.post.presentation.dto.CreatePostRequest;
import com.twin.baribari.post.presentation.dto.UpdatePostRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@WithMockUser
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostJpaRepository postJpaRepository;
    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Nested
    @DisplayName("게시물을 업로드한다")
    class Upload {

        @Test
        void 게시물을_업로드한다() throws Exception {
            // given
            final CreatePostRequest request = PostFixture.createRequest();
            final long memberId = 1L;

            // when
            final ResultActions response = mockMvc.perform(post("/posts")
                .queryParam("memberId", String.valueOf(memberId))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

            // then
            response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").isNumber());

            List<PostJpaEntity> posts = postJpaRepository.findAll();
            assertThat(posts).hasSize(1);
            assertThat(posts.get(0).getTitle()).isEqualTo(request.title());
            assertThat(posts.get(0).getMemberId()).isEqualTo(memberId);

            List<CourseJpaEntity> courses = courseJpaRepository.findAll();
            assertThat(courses).hasSize(1);
            assertThat(courses.get(0).getTitle()).isEqualTo(request.courseTitle());
            assertThat(courses.get(0).getPins()).hasSize(2);
        }
    }

    @Nested
    @DisplayName("모든 게시물을 조회한다")
    class GetAll {

        @Test
        void 모든_게시물을_조회한다() throws Exception {
            // given
            final CourseJpaEntity course = courseJpaRepository.save(CourseFixture.entity());
            final PostJpaEntity post = postJpaRepository.save(PostFixture.entity(course.getId()));

            // when & then
            mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$.data[0].memberId").value(post.getMemberId()));
        }

        @Test
        void 게시물이_없으면_빈_배열을_반환한다() throws Exception {
            // when
            final ResultActions response = mockMvc.perform(get("/posts"));

            // then
            response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(0));
        }
    }

    @Nested
    @DisplayName("게시물 단건을 조회한다")
    class GetById {

        @Test
        void 게시물_단건을_조회한다() throws Exception {
            // given
            final CourseJpaEntity course = courseJpaRepository.save(CourseFixture.entity());
            final PostJpaEntity post = postJpaRepository.save(PostFixture.entity(course.getId()));

            // when
            final ResultActions response = mockMvc.perform(get("/posts/{id}", post.getId()));

            // then
            response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(post.getId()))
                .andExpect(jsonPath("$.data.title").value(post.getTitle()))
                .andExpect(jsonPath("$.data.body").value(post.getBody()))
                .andExpect(jsonPath("$.data.memberId").value(post.getMemberId()));
        }

        @Test
        void 존재하지_않는_게시물을_조회하면_404를_반환한다() throws Exception {
            // given
            final long unsavedId = 999L;

            // when
            final ResultActions response = mockMvc.perform(get("/posts/{id}", unsavedId));

            // then
            response
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").value("POST_NOT_FOUND"));
        }
    }

    @Nested
    @DisplayName("PATCH /posts/{id}")
    class Update {

        @Test
        void 게시물을_수정한다() throws Exception {
            // given
            final CourseJpaEntity course = courseJpaRepository.save(CourseFixture.entity());
            final PostJpaEntity post = postJpaRepository.save(PostFixture.entity(course.getId()));
            final UpdatePostRequest request = new UpdatePostRequest("수정된 제목", "수정된 내용");

            // when
            final ResultActions response = mockMvc.perform(patch("/posts/{id}", post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

            // then
            response.andExpect(status().isNoContent());

            final PostJpaEntity updated = postJpaRepository.findById(post.getId()).orElseThrow();
            assertThat(updated.getTitle()).isEqualTo("수정된 제목");
            assertThat(updated.getBody()).isEqualTo("수정된 내용");
        }

        @Test
        void 존재하지_않는_게시물을_수정하면_404를_반환한다() throws Exception {
            // given
            final long unsavedId = 999L;
            final UpdatePostRequest request = new UpdatePostRequest("수정된 제목", "수정된 내용");

            // when
            final ResultActions response = mockMvc.perform(patch("/posts/{id}", unsavedId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

            // then
            response
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").value("POST_NOT_FOUND"));
        }
    }

    @Nested
    @DisplayName("DELETE /posts/{id}")
    class Delete {

        @Test
        void 게시물을_삭제한다() throws Exception {
            // given
            final CourseJpaEntity course = courseJpaRepository.save(CourseFixture.entity());
            final PostJpaEntity post = postJpaRepository.save(PostFixture.entity(course.getId()));

            // when
            mockMvc.perform(delete("/posts/{id}", post.getId()))
                .andExpect(status().isNoContent());

            // then
            assertThat(postJpaRepository.findById(post.getId())).isEmpty();
            assertThat(courseJpaRepository.findById(course.getId())).isEmpty();
        }

        @Test
        void 존재하지_않는_게시물을_삭제하면_404를_반환한다() throws Exception {
            // given
            final long unsavedId = 999L;

            // when
            final ResultActions response = mockMvc.perform(delete("/posts/{id}", unsavedId));

            // then
            response
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").value("POST_NOT_FOUND"));
        }
    }
}
