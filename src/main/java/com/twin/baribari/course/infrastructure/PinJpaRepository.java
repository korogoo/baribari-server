package com.twin.baribari.course.infrastructure;

import com.twin.baribari.course.infrastructure.entity.PinJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinJpaRepository extends JpaRepository<PinJpaEntity, Long> {
}
