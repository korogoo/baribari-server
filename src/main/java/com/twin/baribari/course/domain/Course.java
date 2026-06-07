package com.twin.baribari.course.domain;

import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Course {

    private final Long id;
    private final String imageUrl;
    private final String title;
    private final String description;
    private final Pins pins;

    public Course(
        final Long id,
        final String imageUrl,
        final String title,
        final String description,
        final Pins pins
    ) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.pins = pins;

        validateArguments();
    }

    public Course(
        final String imageUrl,
        final String title,
        final String description,
        final Pins pins
    ) {
        this(null, imageUrl, title, description, pins);
    }

    public List<Pin> getPins() {
        return pins.toList();
    }

    private void validateArguments() {
        Objects.requireNonNull(imageUrl, "코스 imageUrl 은 필수값입니다.");
        Objects.requireNonNull(title, "코스 title 은 필수값입니다.");
        Objects.requireNonNull(description, "코스 description 은 필수값입니다.");
        Objects.requireNonNull(pins, "코스 pins 는 필수값입니다.");
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Course course = (Course) other;
        return id != null && Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
