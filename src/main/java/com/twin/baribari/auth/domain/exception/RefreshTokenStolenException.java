package com.twin.baribari.auth.domain.exception;

public class RefreshTokenStolenException extends RuntimeException {

    public RefreshTokenStolenException() {
        super("탈취된 리프레시 토큰입니다.");
    }
}
