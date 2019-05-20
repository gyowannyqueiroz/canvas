package com.deutschebank.canvas.model;

import java.util.ArrayList;
import java.util.List;

public class Drawing {

    private final Canvas canvas;
    private final List<Pencil> pencils = new ArrayList<>();
    private char[][] pixels = new char[0][0];

    public Drawing(Canvas canvas) {
        this.canvas = canvas;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public List<Pencil> getPencils() {
        return pencils;
    }

    public void setPixels(char[][] pixels) {
        this.pixels = pixels;
    }

    public char[][] getPixels() {
        return pixels;
    }
}
