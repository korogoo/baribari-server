package com.twin.baribari.course.domain;

public interface CourseRepository {

    long save(Course course);

    boolean existsById(long id);

    Course getById(long id);

    void deleteById(long id);
}
