package com.twin.baribari.course.domain;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Pin {

    private static final double MIN_LATITUDE = -90;
    private static final double MAX_LATITUDE = 90;
    private static final double MIN_LONGITUDE = -180;
    private static final double MAX_LONGITUDE = 180;

    private final Long id;
    private final double latitude;
    private final double longitude;
    private final Sequence sequence;

    public Pin(final double latitude, final double longitude, final int sequence) {
        this(null, latitude, longitude, sequence);
    }

    public Pin(final Long id, final double latitude, final double longitude, final int sequence) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sequence = new Sequence(sequence);

        validateArguments();
    }

    public int sequenceValue() {
        return sequence.value();
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Pin pin = (Pin) other;
        return Objects.equals(id, pin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    private void validateArguments() {
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE) {
            throw new IllegalArgumentException("위도는 -90 ~ 90 사이여야 합니다.");
        }
        if (longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            throw new IllegalArgumentException("경도는 -180 ~ 180 사이여야 합니다.");
        }
    }
}
