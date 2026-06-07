package com.twin.baribari.course.presentation.dto;

import com.twin.baribari.course.application.dto.PinRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateCourseRequest(
    @NotBlank(message = "코스 이미지 URL은 필수입니다.")
    String imageUrl,

    @NotBlank(message = "코스 제목은 필수입니다.")
    String title,

    String description,

    @NotNull(message = "핀 목록은 필수입니다.")
    @Size(min = 2, message = "핀은 최소 2개 이상이어야 합니다.")
    @Valid
    List<CreatePinRequest> pins
) {

    public List<PinRequest> pinRequests() {
        return pins.stream()
            .map(CreatePinRequest::toPinRequest)
            .toList();
    }
}
