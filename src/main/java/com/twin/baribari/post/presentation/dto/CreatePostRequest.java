package com.twin.baribari.post.presentation.dto;

import com.twin.baribari.course.application.dto.PinRequest;
import com.twin.baribari.course.presentation.dto.CreateCourseRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreatePostRequest(
    @NotBlank(message = "제목은 필수입니다.")
    String title,

    String body,

    @NotNull(message = "코스 정보는 필수입니다.")
    @Valid
    CreateCourseRequest course
) {

    public String courseImageUrl() {
        return course.imageUrl();
    }

    public String courseTitle() {
        return course.title();
    }

    public String courseDescription() {
        return course.description();
    }

    public List<PinRequest> coursePinRequests() {
        return course.pinRequests();
    }
}
