package com.deutschebank.canvas.facade;

import com.deutschebank.canvas.model.Drawing;
import com.deutschebank.canvas.model.Pencil;

import java.util.Optional;

public interface DrawFacade<OUTPUT> {

    Drawing createDrawing(Integer width, Integer height);

    OUTPUT drawLine(Drawing drawing, Optional<Pencil> pencil);

    OUTPUT drawRectangle(Drawing drawing, Pencil rectangle);
}
