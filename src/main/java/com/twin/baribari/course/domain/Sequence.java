package com.twin.baribari.course.domain;

public record Sequence(
    int value
) {

    public Sequence {
        if (value < 0) {
            throw new IllegalArgumentException("sequence는 0 이상이어야 합니다.");
        }
    }
}
