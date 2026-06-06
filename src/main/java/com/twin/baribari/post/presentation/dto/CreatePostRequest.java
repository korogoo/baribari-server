package com.twin.baribari.post.presentation.dto;

import com.twin.baribari.course.application.dto.PinRequest;
import com.twin.baribari.course.presentation.dto.CreateCourseRequest;
import java.util.List;

public record CreatePostRequest(
    String title,
    String body,
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
