package com.twin.baribari.course.presentation.dto;

import com.twin.baribari.course.application.dto.PinRequest;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

public record CreatePinRequest(
    @DecimalMin(value = "-90.0", message = "위도는 -90 이상이어야 합니다.")
    @DecimalMax(value = "90.0", message = "위도는 90 이하이어야 합니다.")
    double latitude,

    @DecimalMin(value = "-180.0", message = "경도는 -180 이상이어야 합니다.")
    @DecimalMax(value = "180.0", message = "경도는 180 이하이어야 합니다.")
    double longitude
) {

    public PinRequest toPinRequest() {
        return new PinRequest(latitude, longitude);
    }
}
