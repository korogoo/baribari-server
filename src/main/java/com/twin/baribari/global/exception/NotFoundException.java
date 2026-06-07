package com.twin.baribari.global.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String code;

    public NotFoundException(final String code, final String message) {
        super(message);
        this.code = code;
    }
}
