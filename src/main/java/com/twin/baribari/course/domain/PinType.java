package com.twin.baribari.course.domain;

public enum PinType {
    START, WAYPOINT, END;

    public boolean isStart() {
        return this == START;
    }

    public boolean isEnd() {
        return this == END;
    }
}
