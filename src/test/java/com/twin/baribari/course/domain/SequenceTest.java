package com.twin.baribari.course.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SequenceTest {

    @Test
    void 순서는_0_이상으로_생성할_수_있다() {
        assertThatCode(() -> new Sequence(0))
            .doesNotThrowAnyException();
    }

    @Test
    void 순서가_음수면_예외가_발생한다() {
        assertThatThrownBy(() -> new Sequence(-1))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
