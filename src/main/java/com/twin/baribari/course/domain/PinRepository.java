package com.twin.baribari.course.domain;

public interface PinRepository {

    long save(Pin pin, long courseId);
}
