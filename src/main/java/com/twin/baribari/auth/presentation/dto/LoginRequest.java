package com.twin.baribari.auth.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "identityToken은 필수입니다.")
    String identityToken,

    @NotBlank(message = "fullName은 필수입니다.")
    String fullName
) {
}
