package com.twin.baribari.course.presentation.dto;

import com.twin.baribari.course.application.dto.PinRequest;
import java.util.List;

public record CreateCourseRequest(
    String imageUrl,
    String title,
    String description,
    List<CreatePinRequest> pins
) {

    public List<PinRequest> pinRequests() {
        return pins.stream()
            .map(CreatePinRequest::toPinRequest)
            .toList();
    }
}
