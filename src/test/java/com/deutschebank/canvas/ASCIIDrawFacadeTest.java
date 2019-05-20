package com.deutschebank.canvas;

import com.deutschebank.canvas.facade.ASCIIDrawFacade;
import com.deutschebank.canvas.model.Coordinates;
import com.deutschebank.canvas.model.Drawing;
import com.deutschebank.canvas.model.Pencil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ASCIIDrawFacadeTest {

    @Rule
    public ExpectedException expectedExceptionRule = ExpectedException.none();

    private ASCIIDrawFacade underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new ASCIIDrawFacade();
    }

    @Test
    public void create_and_print_an_empty_canvas() throws Exception {
        // Given
        Drawing drawing = createAndAssertDrawing(5, 3);
        String expectedCanvas = new StringBuffer()
                .append("-------\n")
                .append("|     |\n")
                .append("|     |\n")
                .append("|     |\n")
                .append("-------")
                .toString();

        // When
        StringBuffer canvasOutput = underTest.drawLine(drawing, Optional.empty());

        // Then
        assertThat(canvasOutput.toString()).isEqualTo(expectedCanvas);
    }

    @Test
    public void create_canvas_should_throw_exception_for_negative_width() throws Exception {
        // Given
        expectedExceptionRule.expect(IllegalArgumentException.class);
        expectedExceptionRule.expectMessage("Width must be a valid number greater than zero");

        // When
        underTest.createDrawing(-5, 3);
    }

    @Test
    public void create_canvas_should_throw_exception_for_negative_height() throws Exception {
        // Given
        expectedExceptionRule.expect(IllegalArgumentException.class);
        expectedExceptionRule.expectMessage("Height must be a valid number greater than zero");

        // When
        underTest.createDrawing(5, -3);
    }

    @Test
    public void create_canvas_should_throw_exception_for_zero_width() throws Exception {
        // Given
        expectedExceptionRule.expect(IllegalArgumentException.class);
        expectedExceptionRule.expectMessage("Width must be a valid number greater than zero");

        // When
        underTest.createDrawing(0, -3);
    }

    @Test
    public void create_canvas_should_throw_exception_for_zero_height() throws Exception {
        // Given
        expectedExceptionRule.expect(IllegalArgumentException.class);
        expectedExceptionRule.expectMessage("Height must be a valid number greater than zero");

        // When
        underTest.createDrawing(5, 0);
    }

    @Test
    public void create_canvas_should_throw_exception_for_null_height() throws Exception {
        // Given
        expectedExceptionRule.expect(IllegalArgumentException.class);
        expectedExceptionRule.expectMessage("Height must be a valid number greater than zero");

        // When
        underTest.createDrawing(5, null);
    }

    @Test
    public void create_canvas_should_throw_exception_for_null_width() throws Exception {
        // Given
        expectedExceptionRule.expect(IllegalArgumentException.class);
        expectedExceptionRule.expectMessage("Width must be a valid number greater than zero");

        // When
        underTest.createDrawing(null, 3);
    }

    @Test
    public void draw_an_horizontal_line_with_valid_dimensions_within_canvas() {
        // Given
        Drawing drawing = createAndAssertDrawing(20, 5);
        Optional<Pencil> pencil = Optional.of(new Pencil(Coordinates.of(1, 3), Coordinates.of(7, 3)));
        String expectedOutput = new StringBuffer()
                .append("----------------------\n")
                .append("|                    |\n")
                .append("|                    |\n")
                .append("|xxxxxxx             |\n")
                .append("|                    |\n")
                .append("|                    |\n")
                .append("----------------------")
                .toString();


        // When
        StringBuffer output = underTest.drawLine(drawing, pencil);

        // Then
        assertThat(output.toString()).isEqualTo(expectedOutput);
    }

    @Test
    public void should_draw_an_horizontal_and_vertical_line_with_valid_dimensions_whithin_canvas() {
        // Given
        Drawing drawing = createAndAssertDrawing(20, 5);
        Optional<Pencil> horizontalPencil = Optional.of(new Pencil(Coordinates.of(1, 3), Coordinates.of(7, 3)));
        Optional<Pencil> verticalPencil = Optional.of(new Pencil(Coordinates.of(7, 1), Coordinates.of(7, 3)));
        String expectedOutput = new StringBuffer()
                .append("----------------------\n")
                .append("|      x             |\n")
                .append("|      x             |\n")
                .append("|xxxxxxx             |\n")
                .append("|                    |\n")
                .append("|                    |\n")
                .append("----------------------")
                .toString();
        underTest.drawLine(drawing, horizontalPencil);

        // When
        StringBuffer output = underTest.drawLine(drawing, verticalPencil);

        // Then
        assertThat(drawing.getPencils()).hasSize(2);
        assertThat(output.toString()).isEqualTo(expectedOutput);
    }

    @Test
    public void should_draw_a_rectangle_with_valid_dimensions_whithin_canvas() {
        // Given
        Drawing drawing = createAndAssertDrawing(20, 5);
        Pencil rectanglePencil = new Pencil(Coordinates.of(15,2), Coordinates.of(20,5));
        String expectedOutput = new StringBuffer()
                .append("----------------------\n")
                .append("|                    |\n")
                .append("|              xxxxxx|\n")
                .append("|              x    x|\n")
                .append("|              x    x|\n")
                .append("|              xxxxxx|\n")
                .append("----------------------")
                .toString();

        // When
        StringBuffer output = underTest.drawRectangle(drawing, rectanglePencil);

        // Then
        assertThat(drawing.getPencils()).hasSize(4);
        assertThat(output.toString()).isEqualTo(expectedOutput);
    }

    @Test
    public void lines_already_drawn_should_not_be_erased_or_replaced_with_empty_spaces_by_subsequent_draws() {
        // I caught this bug while doing some manual testing. The top line of the rectangle was removing the
        // second line of the vertical line which is drawn before it.

        // Given
        Drawing drawing = createAndAssertDrawing(20, 5);
        Optional<Pencil> horizontalPencil = Optional.of(new Pencil(Coordinates.of(1, 3), Coordinates.of(7, 3)));
        Optional<Pencil> verticalPencil = Optional.of(new Pencil(Coordinates.of(7, 1), Coordinates.of(7, 3)));
        Pencil rectanglePencil = new Pencil(Coordinates.of(15,2), Coordinates.of(20,5));
        String expectedOutput = new StringBuffer()
                .append("----------------------\n")
                .append("|      x             |\n")
                .append("|      x       xxxxxx|\n")
                .append("|xxxxxxx       x    x|\n")
                .append("|              x    x|\n")
                .append("|              xxxxxx|\n")
                .append("----------------------")
                .toString();

        underTest.drawLine(drawing, horizontalPencil);
        underTest.drawLine(drawing, verticalPencil);

        // When
        StringBuffer output = underTest.drawRectangle(drawing, rectanglePencil);

        // Then
        assertThat(drawing.getPencils()).hasSize(6);
        assertThat(output.toString()).isEqualTo(expectedOutput);
    }

    @Test
    public void a_line_can_overlap_an_existing_one() {
        // Given
        Drawing drawing = createAndAssertDrawing(20, 5);
        Pencil rectanglePencil = new Pencil(Coordinates.of(15,2), Coordinates.of(20,5));
        String expectedOutput = new StringBuffer()
                .append("----------------------\n")
                .append("|                    |\n")
                .append("|xxxxxxxxxxxxxxxxxxxx|\n")
                .append("|              x    x|\n")
                .append("|              x    x|\n")
                .append("|              xxxxxx|\n")
                .append("----------------------")
                .toString();
        underTest.drawRectangle(drawing, rectanglePencil);
        Pencil overlapLine = new Pencil(Coordinates.of(1,2), Coordinates.of(20,2));

        // When
        StringBuffer output = underTest.drawLine(drawing, Optional.of(overlapLine));

        // Then
        assertThat(drawing.getPencils()).hasSize(5);
        assertThat(output.toString()).isEqualTo(expectedOutput);
    }

    @Test
    public void should_throw_exception_when_rectangle_coordinates_are_out_of_bounds() {
        // Given
        Drawing drawing = createAndAssertDrawing(20, 5);
        Pencil pencil = new Pencil(Coordinates.of(10,3), Coordinates.of(25,2));
        expectedExceptionRule.expect(IllegalArgumentException.class);
        expectedExceptionRule.expectMessage("The shape is bigger than the available drawing area");

        // When
        underTest.drawRectangle(drawing, pencil);
    }

    @Test
    public void should_throw_exception_when_line_coordinates_are_out_of_bounds() {
        // Given
        Drawing drawing = createAndAssertDrawing(20, 5);
        Pencil pencil = new Pencil(Coordinates.of(-1,5), Coordinates.of(15,5));
        expectedExceptionRule.expect(IllegalArgumentException.class);
        expectedExceptionRule.expectMessage("The shape is bigger than the available drawing area");

        // When
        underTest.drawLine(drawing, Optional.of(pencil));
    }

    @Test
    public void should_throw_exception_when_left_is_bigger_than_right() {
        // Given
        Drawing drawing = createAndAssertDrawing(20, 5);
        Pencil pencil = new Pencil(Coordinates.of(10,5), Coordinates.of(2,5));
        expectedExceptionRule.expect(IllegalArgumentException.class);
        expectedExceptionRule.expectMessage("Left position is bigger than the right one");

        // When
        underTest.drawLine(drawing, Optional.of(pencil));
    }

    private Drawing createAndAssertDrawing(Integer width, Integer height) {
        Drawing drawing = underTest.createDrawing(width, height);
        assertThat(drawing.getPencils()).isEmpty();
        return drawing;
    }
}