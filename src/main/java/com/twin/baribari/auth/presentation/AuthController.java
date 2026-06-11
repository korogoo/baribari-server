package com.twin.baribari.auth.presentation;

import com.twin.baribari.auth.application.AuthApplicationService;
import com.twin.baribari.auth.application.dto.TokenResponse;
import com.twin.baribari.auth.presentation.dto.LoginRequest;
import com.twin.baribari.auth.presentation.dto.ReissueRequest;
import com.twin.baribari.global.presentation.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthApplicationService authApplicationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody final LoginRequest request) {
        final TokenResponse response = authApplicationService.login(request.identityToken(), request.fullName());
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(@Valid @RequestBody final ReissueRequest request) {
        final TokenResponse response = authApplicationService.reissue(request.refreshToken());
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        // TODO: SecurityContext에서 memberId 추출 (JWT Filter 구현 후)
        return ResponseEntity.ok(ApiResponse.of(null));
    }
}
