package com.deutschebank.canvas.model;

import java.util.Objects;

public class Pencil {

    private final Coordinates start;
    private final Coordinates end;

    public Pencil(Coordinates start, Coordinates end) {
        this.start = start;
        this.end = end;
    }

    public Coordinates getStart() {
        return start;
    }

    public Coordinates getEnd() {
        return end;
    }

    public boolean isVertical() {
        return start.getX() == end.getX();
    }

}
