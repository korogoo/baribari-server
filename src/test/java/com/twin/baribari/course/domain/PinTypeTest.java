package com.twin.baribari.course.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

class PinTypeTest {

    @Nested
    @DisplayName("핀의 타입이 START인지 조회한다")
    class IsStart {

        @Test
        void 타입이_START면_TRUE를_반환한다() {
            // given
            PinType pinType = PinType.START;

            // when
            final boolean isStart = pinType.isStart();

            // then
            assertThat(isStart).isTrue();
        }

        @ParameterizedTest
        @EnumSource(value = PinType.class, mode = Mode.EXCLUDE, names = "START")
        void 타입이_START가_아니면_FALSE를_반환한다(final PinType pinType) {
            // when
            final boolean isStart = pinType.isStart();

            // then
            assertThat(isStart).isFalse();
        }
    }

    @Nested
    @DisplayName("핀의 타입이 END인지 조회한다")
    class IsEnd {

        @Test
        void 타입이_END면_TRUE를_반환한다() {
            // given
            PinType pinType = PinType.END;

            // when
            final boolean isEnd = pinType.isEnd();

            // then
            assertThat(isEnd).isTrue();
        }

        @ParameterizedTest
        @EnumSource(value = PinType.class, mode = Mode.EXCLUDE, names = "END")
        void 타입이_END가_아니면_FALSE를_반환한다(final PinType pinType) {
            // when
            final boolean isEnd = pinType.isEnd();

            // then
            assertThat(isEnd).isFalse();
        }
    }
}
