package com.twin.baribari.post.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostJpaRepository postJpaRepository;

    @Autowired
    private CourseJpaRepository courseJpaRepository;

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
            .andExpect(jsonPath("$.id").isNumber());

        List<PostJpaEntity> posts = postJpaRepository.findAll();
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTitle()).isEqualTo(request.title());
        assertThat(posts.get(0).getMemberId()).isEqualTo(memberId);

        List<CourseJpaEntity> courses = courseJpaRepository.findAll();
        assertThat(courses).hasSize(1);
        assertThat(courses.get(0).getTitle()).isEqualTo(request.courseTitle());
        assertThat(courses.get(0).getPins()).hasSize(2);
    }

    @Test
    void 모든_게시물을_조회한다() throws Exception {
        // given
        final CourseJpaEntity course = courseJpaRepository.save(CourseFixture.entity());
        final PostJpaEntity post = postJpaRepository.save(PostFixture.entity(course.getId()));

        // when & then
        mockMvc.perform(get("/posts"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].title").value(post.getTitle()))
            .andExpect(jsonPath("$[0].memberId").value(post.getMemberId()));
    }

    @Test
    void 게시물_단건을_조회한다() throws Exception {
        // given
        final CourseJpaEntity course = courseJpaRepository.save(CourseFixture.entity());
        final PostJpaEntity post = postJpaRepository.save(PostFixture.entity(course.getId()));

        // when & then
        mockMvc.perform(get("/posts/{id}", post.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(post.getId()))
            .andExpect(jsonPath("$.title").value(post.getTitle()))
            .andExpect(jsonPath("$.body").value(post.getBody()))
            .andExpect(jsonPath("$.memberId").value(post.getMemberId()));
    }
}
