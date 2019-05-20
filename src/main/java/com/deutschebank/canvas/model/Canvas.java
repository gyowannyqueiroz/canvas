package com.deutschebank.canvas.model;

public class Canvas {

    private final Integer width;
    private final Integer height;

    public Canvas(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
