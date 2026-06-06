package com.twin.baribari.post.application;

import com.twin.baribari.course.application.CourseService;
import com.twin.baribari.global.application.dto.ResourceIdResponse;
import com.twin.baribari.post.application.dto.PostDetailResponse;
import com.twin.baribari.post.application.dto.PostSummaryResponse;
import com.twin.baribari.post.presentation.dto.CreatePostRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostApplicationService {

    private final PostService postService;
    private final CourseService courseService;

    @Transactional(readOnly = true)
    public PostDetailResponse getById(final long id) {
        return PostDetailResponse.from(postService.getById(id));
    }

    @Transactional(readOnly = true)
    public List<PostSummaryResponse> getAll() {
        return postService.getAll().stream()
            .map(PostSummaryResponse::from)
            .toList();
    }

    @Transactional
    public ResourceIdResponse upload(final CreatePostRequest request, final long memberId) {
        long courseId = courseService.upload(
            request.courseImageUrl(),
            request.courseTitle(),
            request.courseDescription(),
            request.coursePinRequests()
        );

        long postId = postService.upload(
            request.title(),
            request.body(),
            memberId,
            courseId
        );
        return new ResourceIdResponse(postId);
    }
}
