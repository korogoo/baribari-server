package com.twin.baribari.course.presentation.dto;

import com.twin.baribari.course.application.dto.PinRequest;

public record CreatePinRequest(
    double latitude,
    double longitude
) {

    public PinRequest toPinRequest() {
        return new PinRequest(latitude, longitude);
    }
}
