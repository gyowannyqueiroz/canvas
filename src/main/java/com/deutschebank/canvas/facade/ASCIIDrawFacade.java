package com.deutschebank.canvas.facade;

import com.deutschebank.canvas.model.Canvas;
import com.deutschebank.canvas.model.Coordinates;
import com.deutschebank.canvas.model.Drawing;
import com.deutschebank.canvas.model.Pencil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ASCIIDrawFacade implements DrawFacade<StringBuffer> {

    private static final String CANVAS_BORDER_LINE_SYMBOL = "-";
    private static final String CANVAS_BORDER_COLUMN_SYMBOL = "|";
    private static final String LINE_SYMBOL = "x";
    private static final String EMPTY_SPACE = " ";

    public Drawing createDrawing(Integer width, Integer height) {
        validateDimension(width, height);

        Drawing drawing = new Drawing(new Canvas(width, height));
        char[][] pixels = createBlankCanvasArray(height + 2, width + 2);
        drawing.setPixels(pixels);

        return drawing;
    }

    public StringBuffer drawLine(Drawing drawing, Optional<Pencil> pencil) {

        pencil.ifPresent(pencil1 -> {
            validateBoundaries(drawing, pencil1);

            drawing.getPencils().add(pencil1);

            if (pencil1.isVertical()) {
                drawVerticalLine(pencil1.getStart().getY(), pencil1.getEnd().getY(), pencil1.getStart().getX(),
                        drawing.getPixels());
            } else {
                drawHorizontalLineSimple(
                        pencil1.getStart().getX(),
                        pencil1.getEnd().getX(),
                        pencil1.getStart().getY(),
                        drawing.getPixels());
            }
        });

        return printCanvas(drawing.getPixels());
    }

    public StringBuffer drawRectangle(Drawing drawing, Pencil rectangle) {
        validateBoundaries(drawing, rectangle);

        Pencil top = new Pencil(Coordinates.of(rectangle.getStart().getX(), rectangle.getStart().getY()),
                Coordinates.of(rectangle.getEnd().getX(), rectangle.getEnd().getY()));
        Pencil left = new Pencil(Coordinates.of(rectangle.getStart().getX(), rectangle.getStart().getY()),
                Coordinates.of(rectangle.getStart().getX(), rectangle.getEnd().getY()));
        Pencil right = new Pencil(Coordinates.of(rectangle.getEnd().getX(), rectangle.getStart().getY()),
                Coordinates.of(rectangle.getEnd().getX(), rectangle.getEnd().getY()));
        Pencil bottom = new Pencil(Coordinates.of(rectangle.getStart().getX(), rectangle.getEnd().getY()),
                Coordinates.of(rectangle.getEnd().getX(), rectangle.getEnd().getY()));

        Stream.of(top, left, right, bottom)
                .forEach(pencil -> drawLine(drawing, Optional.of(pencil)));

        return printCanvas(drawing.getPixels());
    }

    private static void validateBoundaries(Drawing drawing, Pencil pencil) {
        if (pencil.getEnd().getX() > drawing.getCanvas().getWidth()
            || pencil.getStart().getX() < 1
            || pencil.getEnd().getY() > drawing.getCanvas().getHeight()
            || pencil.getStart().getY() < 1){
            throw new IllegalArgumentException("The shape is bigger than the available drawing area");
        }

        if (pencil.getStart().getX().compareTo(pencil.getEnd().getX()) == 1) {
            throw new IllegalArgumentException("Left position is bigger than the right one");
        }
    }

    private static char[][] createBlankCanvasArray(Integer height, Integer width) {
        char[][] pixels = new char[height][width];
        for (int i = 0; i < pixels.length; i++) {
            if (i == 0 || i == pixels.length - 1) {
                pixels[i] = drawTopBottomBorderLine(width).toCharArray();
            } else {
                pixels[i] = drawEmptyLineWithinCanvas(width).toCharArray();
            }
        }

        return pixels;
    }

    private static void drawHorizontalLineSimple(int start, int end, int y, char[][] pixels) {
        for (int i = start; i <= end; i++) {
            String currentPixel = String.valueOf(pixels[y][i]);
            if (currentPixel.equals(EMPTY_SPACE) && i >= start && i <= end) {
                pixels[y][i] = LINE_SYMBOL.charAt(0);
            }
        }
    }

    private static void drawVerticalLine(int y1, int y2, int x, char[][] canvas) {
        for (int y = 0; y < canvas.length; y++) {
            if (y >= y1 && y <=y2) {
                canvas[y][x] = LINE_SYMBOL.charAt(0);
            }
        }
    }

    private static void validateDimension(Integer width, Integer height) {
        if (width == null || width <= 0) {
            throw new IllegalArgumentException("Width must be a valid number greater than zero");
        }
        if (height == null || height <= 0) {
            throw new IllegalArgumentException("Height must be a valid number greater than zero");
        }
    }

    private static StringBuffer printCanvas(char[][] pixels) {
        StringBuffer output = new StringBuffer();
        for (char[] line: pixels) {
            output.append(String.valueOf(line) + "\n");
        }
        output.deleteCharAt(output.length()-1);

        return output;
    }

    private static String drawTopBottomBorderLine(Integer width) {
        return drawLine(width, CANVAS_BORDER_LINE_SYMBOL);
    }

    private static String drawLine(Integer width, String symbol) {
        if (width == 0) {
            return "";
        }
        return String.join("", Collections.nCopies(width, symbol));
    }

    private static String drawEmptyLineWithinCanvas(Integer width) {
        return CANVAS_BORDER_COLUMN_SYMBOL
                + drawLine(width - 2, EMPTY_SPACE)
                + CANVAS_BORDER_COLUMN_SYMBOL;
    }

}
