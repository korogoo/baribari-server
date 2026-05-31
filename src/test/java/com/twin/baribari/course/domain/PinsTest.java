package com.twin.baribari.course.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.twin.baribari.fixture.PinFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PinsTest {

    @Nested
    @DisplayName("핀에는 출발점과 도착점이 필수로 포함되어야 한다")
    class ValidateRequiredPinTypes {

        @Test
        void 출발점_핀과_도착점_핀을_포함하여_생성할_수_있다() {
            // given
            final List<Pin> pins = List.of(
                PinFixture.start(),
                PinFixture.end(1)
            );

            // when & then
            assertThatCode(() -> new Pins(pins))
                .doesNotThrowAnyException();
        }

        @Test
        void 핀의_개수가_2개_미만이면_예외가_발생한다() {
            // given
            final List<Pin> pins = List.of(
                PinFixture.end(2)
            );

            // when & then
            assertThatThrownBy(() -> new Pins(pins))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("2개");
        }
    }
}
