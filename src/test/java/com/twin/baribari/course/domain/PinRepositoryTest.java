package com.twin.baribari.course.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.twin.baribari.course.infrastructure.PinRepositoryImpl;
import com.twin.baribari.fixture.PinFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(PinRepositoryImpl.class)
class PinRepositoryTest {

    @Autowired
    PinRepository pinRepository;

    @Test
    void 핀_정보를_저장한다() {
        // given
        final Pin pin = PinFixture.start();
        final long courseId = 2L;

        // when
        final long savedId = pinRepository.save(pin, courseId);

        // then
        assertThat(savedId).isNotNull();
    }
}
