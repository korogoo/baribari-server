package com.twin.baribari.post.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twin.baribari.course.infrastructure.CourseJpaRepository;
import com.twin.baribari.course.infrastructure.entity.CourseJpaEntity;
import com.twin.baribari.course.presentation.dto.CreateCourseRequest;
import com.twin.baribari.course.presentation.dto.CreatePinRequest;
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
    void upload() throws Exception {
        // given
        CreatePostRequest request = new CreatePostRequest(
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

        // when
        final ResultActions response = mockMvc.perform(post("/posts?memberId=1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

        // then
        response
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNumber());

        List<PostJpaEntity> posts = postJpaRepository.findAll();
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTitle()).isEqualTo("한강 라이딩");
        assertThat(posts.get(0).getMemberId()).isEqualTo(1L);

        List<CourseJpaEntity> courses = courseJpaRepository.findAll();
        assertThat(courses).hasSize(1);
        assertThat(courses.get(0).getTitle()).isEqualTo("한강 코스");
        assertThat(courses.get(0).getPins()).hasSize(2);
    }
}
