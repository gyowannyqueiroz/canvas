package com.deutschebank.canvas.shell;

import com.deutschebank.canvas.facade.DrawFacade;
import com.deutschebank.canvas.model.Coordinates;
import com.deutschebank.canvas.model.Drawing;
import com.deutschebank.canvas.model.Pencil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Optional;

@ShellComponent
public class DrawShellCommand {

    private DrawFacade drawFacade;
    private Drawing drawing;

    @Autowired
    public DrawShellCommand(DrawFacade drawFacade) {
        this.drawFacade = drawFacade;
    }

    @ShellMethod(key = "C", value = "Creates an empty canvas")
    public String createCanvas(
            @ShellOption Integer width,
            @ShellOption Integer height) {
        drawing = drawFacade.createDrawing(width, height);
        return drawFacade.drawLine(drawing, Optional.empty()).toString();
    }

    @ShellMethod(key = "L", value = "Draws a line within the canvas")
    public String drawLine(
            @ShellOption Integer x1,
            @ShellOption Integer y1,
            @ShellOption Integer x2,
            @ShellOption Integer y2) {

        checkCanvasExists();

        Pencil linePencil = new Pencil(Coordinates.of(x1, y1), Coordinates.of(x2, y2));

        return drawFacade.drawLine(drawing, Optional.of(linePencil)).toString();
    }

    @ShellMethod(key = "R", value = "Draws a rectangle within the canvas")
    public String drawRectangle(
            @ShellOption Integer x1,
            @ShellOption Integer y1,
            @ShellOption Integer x2,
            @ShellOption Integer y2) {

        checkCanvasExists();

        Pencil rectanglePencil = new Pencil(Coordinates.of(x1, y1), Coordinates.of(x2, y2));

        return drawFacade.drawRectangle(drawing, rectanglePencil).toString();
    }

    private void checkCanvasExists() {
        if (drawing == null) {
            throw new IllegalArgumentException("The Canvas has not been created. Run C width height");
        }
    }
}
