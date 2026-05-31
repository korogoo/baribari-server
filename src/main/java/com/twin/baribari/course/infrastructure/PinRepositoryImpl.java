package com.twin.baribari.course.infrastructure;

import com.twin.baribari.course.domain.Pin;
import com.twin.baribari.course.domain.PinRepository;
import com.twin.baribari.course.infrastructure.entity.PinJpaEntity;
import com.twin.baribari.course.infrastructure.mapper.PinMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PinRepositoryImpl implements PinRepository {

    private PinJpaRepository pinJpaRepository;

    @Override
    public long save(final Pin pin, final long courseId) {
        final PinJpaEntity saved = pinJpaRepository.save(PinMapper.toEntityForSave(pin, courseId));
        return saved.getId();
    }
}
