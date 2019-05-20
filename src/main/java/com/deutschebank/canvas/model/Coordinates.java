package com.deutschebank.canvas.model;

public class Coordinates {

    private final Integer x;
    private final Integer y;

    private Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public static Coordinates of(Integer x, Integer y) {
        return new Coordinates(x, y);
    }
}
