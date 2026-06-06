package com.twin.baribari.course.domain;

import java.util.List;

public class Pins {

    private static final int MINIMUM_SIZE = 2;

    private final List<Pin> pins;

    public Pins(final List<Pin> pins) {
        this.pins = List.copyOf(pins);
        validateRequiredPinTypes();
    }

    public List<Pin> toList() {
        return List.copyOf(pins);
    }

    private void validateRequiredPinTypes() {
        if (pins.size() < MINIMUM_SIZE) {
            throw new IllegalArgumentException("핀의 개수는 2개 이상이어야 합니다. (출발지, 도착지)");
        }
    }
}
