package com.deutschebank.canvas.shell;

import com.deutschebank.canvas.facade.ASCIIDrawFacade;
import com.deutschebank.canvas.model.Canvas;
import com.deutschebank.canvas.model.Coordinates;
import com.deutschebank.canvas.model.Drawing;
import com.deutschebank.canvas.model.Pencil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class DrawShellCommandTest {

    @MockBean
    private ASCIIDrawFacade drawFacade;

    @Rule
    public ExpectedException expectedExceptionRule = ExpectedException.none();

    private DrawShellCommand underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new DrawShellCommand(drawFacade);
    }

    @Test
    public void should_delegate_create_canvas_command_and_return_a_string() {
        // Given
        Drawing drawing = new Drawing(new Canvas(5,3));
        given(drawFacade.createDrawing(5,3)).willReturn(drawing);
        StringBuffer expectedCanvas = new StringBuffer()
                .append("-------\n")
                .append("|     |\n")
                .append("|     |\n")
                .append("|     |\n")
                .append("-------");
        given(drawFacade.drawLine(drawing, Optional.empty())).willReturn(expectedCanvas);

        // When
        String actualOutput = underTest.createCanvas(5,3);

        // Then
        assertThat(actualOutput).isEqualTo(expectedCanvas.toString());
    }

    @Test
    public void should_delegate_draw_line_command_and_return_a_string_with_draw_in_canvas() {
        // Given
        Drawing drawing = new Drawing(new Canvas(5,3));
        given(drawFacade.createDrawing(5,3)).willReturn(drawing);
        StringBuffer expectedBlankCanvas = new StringBuffer()
                .append("-------\n")
                .append("|     |\n")
                .append("|     |\n")
                .append("|     |\n")
                .append("-------");
        given(drawFacade.drawLine(drawing, Optional.empty())).willReturn(expectedBlankCanvas);
        StringBuffer expectedUpdatedCanvas = new StringBuffer()
                .append("-------\n")
                .append("|     |\n")
                .append("|xxxx |\n")
                .append("|     |\n")
                .append("-------");
        Pencil pencil = new Pencil(Coordinates.of(1,2), Coordinates.of(1,4));
        when(drawFacade.drawLine(any(Drawing.class), any(Optional.class))).thenReturn(expectedUpdatedCanvas);
        underTest.createCanvas(5,3);

        // When
        String actualOutput = underTest.drawLine(1,2,1,4);

        // Then
        assertThat(actualOutput).isEqualTo(expectedUpdatedCanvas.toString());
    }

    @Test
    public void should_delegate_draw_rectangle_command_and_return_a_string_with_draw_in_canvas() {
        // Given
        Drawing drawing = new Drawing(new Canvas(5,3));
        given(drawFacade.createDrawing(5,3)).willReturn(drawing);
        StringBuffer expectedBlankCanvas = new StringBuffer()
                .append("-------\n")
                .append("|     |\n")
                .append("|     |\n")
                .append("|     |\n")
                .append("-------");
        given(drawFacade.drawLine(drawing, Optional.empty())).willReturn(expectedBlankCanvas);
        StringBuffer expectedUpdatedCanvas = new StringBuffer()
                .append("-------\n")
                .append("|xxxx |\n")
                .append("|x  x |\n")
                .append("|xxxx |\n")
                .append("-------");
        Pencil pencil = new Pencil(Coordinates.of(1,2), Coordinates.of(3,4));
        when(drawFacade.drawRectangle(any(Drawing.class), any(Pencil.class))).thenReturn(expectedUpdatedCanvas);
        underTest.createCanvas(5,3);

        // When
        String actualOutput = underTest.drawRectangle(1,2,3,4);

        // Then
        assertThat(actualOutput).isEqualTo(expectedUpdatedCanvas.toString());
    }

    @Test
    public void draw_line_should_throw_exception_if_canvas_is_not_created() {
        // Given
        expectedExceptionRule.expect(IllegalArgumentException.class);
        expectedExceptionRule.expectMessage("The Canvas has not been created. Run C width height");

        // When
        underTest.drawLine(1,2,3,4);

    }

    @Test
    public void draw_rectangle_should_throw_exception_if_canvas_is_not_created() {
        // Given
        expectedExceptionRule.expect(IllegalArgumentException.class);
        expectedExceptionRule.expectMessage("The Canvas has not been created. Run C width height");

        // When
        underTest.drawRectangle(1,2,3,4);

    }
}