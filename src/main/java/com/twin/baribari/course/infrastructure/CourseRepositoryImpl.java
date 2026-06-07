package com.twin.baribari.course.infrastructure;

import com.twin.baribari.course.domain.Course;
import com.twin.baribari.course.domain.CourseRepository;
import com.twin.baribari.course.infrastructure.entity.CourseJpaEntity;
import com.twin.baribari.course.infrastructure.mapper.CourseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {

    private final CourseJpaRepository courseJpaRepository;

    @Override
    public Course save(final Course course) {
        final CourseJpaEntity saved = courseJpaRepository.save(CourseMapper.toEntityForSave(course));
        return CourseMapper.toDomain(saved);
    }

    @Override
    public boolean existsById(final long id) {
        return courseJpaRepository.existsById(id);
    }

    @Override
    public Course getById(final long id) {
        final CourseJpaEntity found = courseJpaRepository.findById(id)
            .orElseThrow(RuntimeException::new);
        return CourseMapper.toDomain(found);
    }

    @Override
    public void deleteById(final long id) {
        courseJpaRepository.deleteById(id);
    }
}
