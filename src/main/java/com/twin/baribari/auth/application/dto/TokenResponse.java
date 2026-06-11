package com.twin.baribari.auth.application.dto;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {
}
