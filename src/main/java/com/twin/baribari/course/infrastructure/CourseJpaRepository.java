package com.twin.baribari.course.infrastructure;

import com.twin.baribari.course.infrastructure.entity.CourseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpaRepository extends JpaRepository<CourseJpaEntity, Long> {
}
