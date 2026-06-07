package com.twin.baribari.fixture;

import com.twin.baribari.course.domain.Pins;
import java.util.List;

public class PinsFixture {

    public static Pins pins() {
        return new Pins(List.of(
            PinFixture.start(),
            PinFixture.end(1)
        ));
    }

    public static Pins pinsWithWaypoint() {
        return new Pins(List.of(
            PinFixture.start(),
            PinFixture.waypoint(1),
            PinFixture.end(2)
        ));
    }
}
