package com.twin.baribari.course.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Course {

    private final String imageUrl;
    private final String title;
    private final String description;
    private final Pins pins;

    public Course(
        final String imageUrl,
        final String title,
        final String description,
        final Pins pins
    ) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.pins = pins;

        validateArguments();
    }

    private void validateArguments() {
        Objects.requireNonNull(imageUrl, "코스 imageUrl 은 필수값입니다.");
        Objects.requireNonNull(title, "코스 title 은 필수값입니다.");
        Objects.requireNonNull(description, "코스 description 은 필수값입니다.");
        Objects.requireNonNull(pins, "코스 pins 는 필수값입니다.");
    }
}
