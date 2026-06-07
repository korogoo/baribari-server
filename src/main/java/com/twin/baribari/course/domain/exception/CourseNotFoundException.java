package com.twin.baribari.course.domain.exception;

import com.twin.baribari.global.exception.NotFoundException;

public class CourseNotFoundException extends NotFoundException {

    public CourseNotFoundException(final long id) {
        super("COURSE_NOT_FOUND", "코스를 찾을 수 없습니다. (id: " + id + ")");
    }
}
