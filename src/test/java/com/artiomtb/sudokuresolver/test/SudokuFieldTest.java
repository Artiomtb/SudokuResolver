package com.artiomtb.sudokuresolver.test;

import com.artiomtb.sudokuresolver.SudokuField;
import com.artiomtb.sudokuresolver.SudokuPoint;
import com.artiomtb.sudokuresolver.exceptions.IncorrectSudokuFieldLineNumberException;
import com.artiomtb.sudokuresolver.exceptions.IncorrectSudokuPointException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SudokuFieldTest {

    private final int correctPosX[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final int correctPosY[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    private SudokuField sudokuEmptyField;
    private SudokuField sudokuNonEmptyField;

    @Before
    public void createFields() throws IncorrectSudokuPointException {
        sudokuEmptyField = new SudokuField();
        List<SudokuPoint> points = new ArrayList<>();
        for (int currentX : correctPosX) {
            for (int currentY : correctPosY) {
                int value = Math.max(currentX, currentY);
                points.add(new SudokuPoint(currentX, currentY, value));
            }
        }
        sudokuNonEmptyField = new SudokuField(points);
    }

    @Test
    public void createEmptySudokuField() throws IncorrectSudokuPointException {
        for (int y = 1; y <= 9; y++) {
            for (int x = 1; x <= 9; x++) {
                SudokuPoint point = sudokuEmptyField.getPoint(x, y);
                assertEquals(point.getPosX(), x);
                assertEquals(point.getPosY(), y);
                assertEquals(point.getValue(), 0);
            }
        }
    }

    @Test
    public void createNonEmptySudokuField() throws IncorrectSudokuPointException {
        for (int currentX : correctPosX) {
            for (int currentY : correctPosY) {
                int value = Math.max(currentX, currentY);
                SudokuPoint point = sudokuNonEmptyField.getPoint(currentX, currentY);
                assertEquals(point.getPosX(), currentX);
                assertEquals(point.getPosY(), currentY);
                assertEquals(point.getValue(), value);
            }
        }
    }

    @Test
    public void setPointTest() throws IncorrectSudokuPointException {
        for (int currentX : correctPosX) {
            for (int currentY : correctPosY) {
                int value = Math.max(currentX, currentY);
                SudokuPoint point = new SudokuPoint(currentX, currentY, value);
                sudokuEmptyField.setPoint(point);
                sudokuNonEmptyField.setPoint(point);
                assertEquals(point, sudokuEmptyField.getPoint(currentX, currentY));
                assertEquals(point, sudokuNonEmptyField.getPoint(currentX, currentY));
                sudokuEmptyField.setPoint(currentX, currentY, 0);
                sudokuNonEmptyField.setPoint(currentX, currentY, 0);
                sudokuEmptyField.setPoint(currentX, currentY, value);
                sudokuNonEmptyField.setPoint(currentX, currentY, value);
                assertEquals(point, sudokuEmptyField.getPoint(currentX, currentY));
                assertEquals(point, sudokuNonEmptyField.getPoint(currentX, currentY));
            }
        }
    }

    @Test
    public void checkValidityTest() throws IncorrectSudokuPointException {
        SudokuField field = new SudokuField();
        assertTrue(field.checkFieldValidity());
        field.setPoint(new SudokuPoint(1, 1, 1));
        field.setPoint(new SudokuPoint(4, 1, 1));
        assertFalse(field.checkFieldValidity());
        field = new SudokuField();
        field.setPoint(new SudokuPoint(1, 1, 1));
        field.setPoint(new SudokuPoint(1, 9, 1));
        assertFalse(field.checkFieldValidity());
        field = new SudokuField();
        field.setPoint(new SudokuPoint(1, 1, 1));
        field.setPoint(new SudokuPoint(3, 3, 1));
        assertFalse(field.checkFieldValidity());
        field = new SudokuField();
        field.setPoint(new SudokuPoint(1, 1, 1));
        field.setPoint(new SudokuPoint(3, 3, 2));
        field.setPoint(new SudokuPoint(3, 5, 1));
        assertTrue(field.checkFieldValidity());
    }

    @Test
    public void checkHorizontalValidityTest() throws IncorrectSudokuPointException, IncorrectSudokuFieldLineNumberException {
        SudokuField field = new SudokuField();
        for (int currentLine = 1; currentLine <= 9; currentLine++) {
            assertTrue(field.checkHorizontalLine(currentLine));
            for (int i = 0; i < 9; i++) {
                field.setPoint(i + 1, currentLine, i + 1);
            }
            assertTrue(field.checkHorizontalLine(currentLine));
            field.setPoint(5, currentLine, 4);
            assertFalse(field.checkHorizontalLine(currentLine));
            field.setPoint(5, currentLine, 0);
            assertTrue(field.checkHorizontalLine(currentLine));
        }
    }

    @Test
    public void checkVerticalValidityTest() throws IncorrectSudokuPointException, IncorrectSudokuFieldLineNumberException {
        SudokuField field = new SudokuField();
        for (int currentLine = 1; currentLine <= 9; currentLine++) {
            assertTrue(field.checkVerticalLine(currentLine));
            for (int i = 0; i < 9; i++) {
                field.setPoint(currentLine, i + 1, i + 1);
            }
            assertTrue(field.checkVerticalLine(currentLine));
            field.setPoint(currentLine, 5, 4);
            assertFalse(field.checkVerticalLine(currentLine));
            field.setPoint(currentLine, 5, 0);
            assertTrue(field.checkVerticalLine(currentLine));
        }
    }

    @Test
    public void checkSquareValidityTest() throws IncorrectSudokuPointException, IncorrectSudokuFieldLineNumberException {
        SudokuField field = new SudokuField();
        for (int currentSquare = 1; currentSquare <= 9; currentSquare++) {
            assertTrue(field.checkSquare(currentSquare));
            int currentStartX;
            int currentStartY;
            int squareMod = currentSquare % 3;
            if (squareMod == 0) {
                currentStartX = 6;
            } else if (squareMod == 1) {
                currentStartX = 0;
            } else {
                currentStartX = 3;
            }
            if (currentSquare <= 3) {
                currentStartY = 0;
            } else if (currentSquare <= 6) {
                currentStartY = 3;
            } else {
                currentStartY = 6;
            }
            int currentValue = 1;
            for (int currentY = currentStartY; currentY < currentStartY + 3; currentY++) {
                for (int currentX = currentStartX; currentX < currentStartX + 3; currentX++) {
                    field.setPoint(currentX + 1, currentY + 1, currentValue++);
                }
            }
            assertTrue(field.checkSquare(currentSquare));
            field.setPoint(currentStartX + 1, currentStartY + 2, 5);
            assertFalse(field.checkSquare(currentSquare));
        }
    }

    @Test
    public void checkAvailableValuesTest() throws IncorrectSudokuPointException, IncorrectSudokuFieldLineNumberException {
        SudokuField field = new SudokuField();
        int[][] coords = {
                {1, 2, 3, 1, 9, 2, 3, 1, 7},
                {2, 1, 3, 9, 1, 3, 1, 5, 1},
                {5, 7, 8, 9, 6, 3, 4, 2, 1}
        };
        Integer[] expectedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < coords[0].length; i++) {
            int posX = coords[0][i];
            int posY = coords[1][i];
            int value = coords[2][i];
            field.setPoint(new SudokuPoint(posX, posY, value));
            Integer[] tempExpectedArray = new Integer[expectedArray.length - 1];
            int tempExpectedArrayCount = 0;
            for (Integer currentValue : expectedArray) {
                if (!currentValue.equals(value)) {
                    tempExpectedArray[tempExpectedArrayCount++] = currentValue;
                }
            }
            expectedArray = tempExpectedArray;
            List<Integer> availableValuesForPoint = field.getAvailableValuesForPoint(1, 1);
            Integer[] actualArray = availableValuesForPoint.toArray(new Integer[availableValuesForPoint.size()]);
            assertArrayEquals(expectedArray, actualArray);
        }
    }
}
