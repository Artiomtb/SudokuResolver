package com.artiomtb.sudokuresolver.test;

import com.artiomtb.sudokuresolver.SudokuField;
import com.artiomtb.sudokuresolver.SudokuPoint;
import com.artiomtb.sudokuresolver.exceptions.SudokuException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class SudokuFieldTest {

    private final int correctPosX[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final int correctPosY[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    private SudokuField sudokuEmptyField;
    private SudokuField sudokuNonEmptyField;
    private SudokuField sudokuNonEmptyArrayField;
    private int[][] correctSudokuArray = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 4, 5, 6, 7, 8, 9, 1},
            {5, 6, 7, 8, 9, 1, 2, 3, 4},
            {8, 9, 1, 2, 3, 4, 5, 6, 7},
            {3, 4, 5, 6, 7, 8, 9, 1, 2},
            {6, 7, 8, 9, 1, 2, 3, 4, 5},
            {9, 1, 2, 3, 4, 5, 6, 7, 8}
    };

    @Before
    public void createFields() throws SudokuException {
        sudokuEmptyField = new SudokuField();
        List<SudokuPoint> points = new ArrayList<>();
        for (int currentX : correctPosX) {
            for (int currentY : correctPosY) {
                int value = Math.max(currentX, currentY);
                points.add(new SudokuPoint(currentX, currentY, value));
            }
        }
        sudokuNonEmptyField = new SudokuField(points);
        sudokuNonEmptyArrayField = new SudokuField(correctSudokuArray);
    }

    @Test
    public void createEmptySudokuField() {
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
    public void createNonEmptySudokuField() {
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
    public void createNonEmptyArraySudokuField() {
        for (int currentX : correctPosX) {
            for (int currentY : correctPosY) {
                SudokuPoint point = sudokuNonEmptyArrayField.getPoint(currentX, currentY);
                assertEquals(point.getPosX(), currentX);
                assertEquals(point.getPosY(), currentY);
                assertEquals(point.getValue(), correctSudokuArray[currentY - 1][currentX - 1]);
            }
        }
    }

    @Test
    public void createEmptyStringSudokuField() throws SudokuException {
        SudokuField field = new SudokuField("000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                assertEquals(0, field.getPoint(x + 1, y + 1).getValue());
            }
        }
    }

    @Test
    public void createNonEmptyStringSudokuField() throws SudokuException {
        String fieldString = "103456789456789103789103456034567891567891034891034567345678910678910345910345678";
        SudokuField field = new SudokuField(fieldString);
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                assertEquals((int) Integer.valueOf(String.valueOf(fieldString.charAt(y * 9 + x))), field.getPoint(x + 1, y + 1).getValue());
            }
        }
    }

    @Test
    public void setPointTest() throws SudokuException {
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
    public void isFieldValidTest() throws SudokuException {
        SudokuField field = new SudokuField();
        assertTrue(field.isFieldValid());
        field.setPoint(new SudokuPoint(1, 1, 1));
        field.setPoint(new SudokuPoint(4, 1, 1));
        assertFalse(field.isFieldValid());
        field = new SudokuField();
        field.setPoint(new SudokuPoint(1, 1, 1));
        field.setPoint(new SudokuPoint(1, 9, 1));
        assertFalse(field.isFieldValid());
        field = new SudokuField();
        field.setPoint(new SudokuPoint(1, 1, 1));
        field.setPoint(new SudokuPoint(3, 3, 1));
        assertFalse(field.isFieldValid());
        field = new SudokuField();
        field.setPoint(new SudokuPoint(1, 1, 1));
        field.setPoint(new SudokuPoint(3, 3, 2));
        field.setPoint(new SudokuPoint(3, 5, 1));
        assertTrue(field.isFieldValid());
    }

    @Test
    public void getAllEmptySudokuPointsTest() throws SudokuException {
        SudokuField field = new SudokuField();
        List<SudokuPoint> emptyPointsList;
        List<SudokuPoint> expectedPointsList = new ArrayList<>();
        for (int currentX : correctPosX) {
            for (int currentY : correctPosY) {
                expectedPointsList.add(new SudokuPoint(currentX, currentY, 0));
            }
        }
        emptyPointsList = field.getAllEmptySudokuPoints();
        SudokuPoint[] emptyPoints = emptyPointsList.toArray(new SudokuPoint[emptyPointsList.size()]);
        SudokuPoint[] expectedPoints = expectedPointsList.toArray(new SudokuPoint[expectedPointsList.size()]);
        assertArrayEquals(expectedPoints, emptyPoints);
        Iterator<SudokuPoint> iter = expectedPointsList.iterator();
        while (iter.hasNext()) {
            SudokuPoint point = iter.next();
            iter.remove();
            expectedPoints = expectedPointsList.toArray(new SudokuPoint[expectedPointsList.size()]);
            for (int currentValue = 1; currentValue <= 9; currentValue++) {
                int posX = point.getPosX();
                int posY = point.getPosY();
                field.setPoint(posX, posY, currentValue);
                emptyPointsList = field.getAllEmptySudokuPoints();
                emptyPoints = emptyPointsList.toArray(new SudokuPoint[emptyPointsList.size()]);
                assertArrayEquals(expectedPoints, emptyPoints);
            }
        }
        expectedPointsList = new ArrayList<>();
        for (int currentX : correctPosX) {
            for (int currentY : correctPosY) {
                field.setPoint(currentX, currentY, 0);
                expectedPointsList.add(new SudokuPoint(currentX, currentY, 0));
                emptyPointsList = field.getAllEmptySudokuPoints();
                emptyPoints = emptyPointsList.toArray(new SudokuPoint[emptyPointsList.size()]);
                expectedPoints = expectedPointsList.toArray(new SudokuPoint[expectedPointsList.size()]);
                assertArrayEquals(expectedPoints, emptyPoints);
            }
        }
    }

    @Test
    public void isHorizontalLineValidTest() throws SudokuException {
        SudokuField field = new SudokuField();
        for (int currentLine = 1; currentLine <= 9; currentLine++) {
            assertTrue(field.isHorizontalLineValid(currentLine));
            for (int i = 0; i < 9; i++) {
                field.setPoint(i + 1, currentLine, i + 1);
            }
            assertTrue(field.isHorizontalLineValid(currentLine));
            field.setPoint(5, currentLine, 4);
            assertFalse(field.isHorizontalLineValid(currentLine));
            field.setPoint(5, currentLine, 0);
            assertTrue(field.isHorizontalLineValid(currentLine));
        }
    }

    @Test
    public void isVerticalLineValidTest() throws SudokuException {
        SudokuField field = new SudokuField();
        for (int currentLine = 1; currentLine <= 9; currentLine++) {
            assertTrue(field.isVerticalLineValid(currentLine));
            for (int i = 0; i < 9; i++) {
                field.setPoint(currentLine, i + 1, i + 1);
            }
            assertTrue(field.isVerticalLineValid(currentLine));
            field.setPoint(currentLine, 5, 4);
            assertFalse(field.isVerticalLineValid(currentLine));
            field.setPoint(currentLine, 5, 0);
            assertTrue(field.isVerticalLineValid(currentLine));
        }
    }

    @Test
    public void isSquareValidTest() throws SudokuException {
        SudokuField field = new SudokuField();
        for (int currentSquare = 1; currentSquare <= 9; currentSquare++) {
            assertTrue(field.isSquareValid(currentSquare));
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
            assertTrue(field.isSquareValid(currentSquare));
            field.setPoint(currentStartX + 1, currentStartY + 2, 5);
            assertFalse(field.isSquareValid(currentSquare));
        }
    }

    @Test
    public void checkAvailableValuesTest() throws SudokuException {
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

    @Test
    public void isSolvedTest() throws SudokuException {
        SudokuField field = new SudokuField();
        assertFalse(field.isSolved());
        field = new SudokuField(correctSudokuArray);
        assertTrue(field.isSolved());
        SudokuPoint point = field.getPoint(9, 9);
        field.setPoint(9, 9, 0);
        assertFalse(field.isSolved());
        field.setPoint(point);
        assertTrue(field.isSolved());
    }

    @Test
    public void fieldCloneTest() throws SudokuException {
        SudokuField field = new SudokuField();
        SudokuField clonedField = field.clone();
        for (int y = 1; y <= 9; y++) {
            for (int x = 1; x <= 9; x++) {
                assertEquals(field.getPoint(x, y), clonedField.getPoint(x, y));
            }
        }
        field = new SudokuField(correctSudokuArray);
        clonedField = field.clone();
        for (int y = 1; y <= 9; y++) {
            for (int x = 1; x <= 9; x++) {
                assertEquals(field.getPoint(x, y), clonedField.getPoint(x, y));
            }
        }
        for (int y = 1; y <= 9; y++) {
            for (int x = 1; x <= 9; x++) {
                for (int value = 0; value <= 9; value++) {
                    field.setPoint(x, y, value);
                    clonedField.setPoint(x, y, (value + 1) % 9);
                    assertNotEquals(field.getPoint(x, y).getValue(), clonedField.getPoint(x, y).getValue());
                }
            }
        }
    }

    @Test
    public void getEasyStringTest() throws SudokuException {
        SudokuField field = new SudokuField();
        String expectedString = "000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        String actualString;
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                for (int value = 1; value <= 9; value++) {
                    int pos = y * 9 + x;
                    field.setPoint(x + 1, y + 1, value);
                    expectedString = expectedString.substring(0, pos) + value + expectedString.substring(pos + 1);
                    actualString = field.toEasyString();
                    assertEquals(expectedString, actualString);
                }
            }
        }
    }

    @Test(expected = SudokuException.class)
    public void createSudokuFieldWithIncorrectYLowerArray() throws SudokuException {
        int[][] incorrectArray = new int[][]{
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {2, 3, 4, 5, 6, 7, 8, 9, 1},
                {5, 6, 7, 8, 9, 1, 2, 3, 4},
                {8, 9, 1, 2, 3, 4, 5, 6, 7},
                {3, 4, 5, 6, 7, 8, 9, 1, 2},
                {6, 7, 8, 9, 1, 2, 3, 4, 5}
        };
        SudokuField field = new SudokuField(incorrectArray);
    }

    @Test(expected = SudokuException.class)
    public void createSudokuFieldWithIncorrectYBiggerArray() throws SudokuException {
        int[][] incorrectArray = new int[][]{
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {2, 3, 4, 5, 6, 7, 8, 9, 1},
                {5, 6, 7, 8, 9, 1, 2, 3, 4},
                {8, 9, 1, 2, 3, 4, 5, 6, 7},
                {3, 4, 5, 6, 7, 8, 9, 1, 2},
                {6, 7, 8, 9, 1, 2, 3, 4, 5},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {7, 8, 9, 1, 2, 3, 4, 5, 6}
        };
        SudokuField field = new SudokuField(incorrectArray);
    }

    @Test(expected = SudokuException.class)
    public void createSudokuFieldWithIncorrectXBiggerArray() throws SudokuException {
        int[][] incorrectArray = new int[][]{
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {2, 3, 4, 5, 6, 7, 8, 9, 1},
                {5, 6, 7, 8, 9, 1, 2, 3, 4, 5},
                {8, 9, 1, 2, 3, 4, 5, 6, 7},
                {3, 4, 5, 6, 7, 8, 9, 1, 2},
                {6, 7, 8, 9, 1, 2, 3, 4, 5},
                {7, 8, 9, 1, 2, 3, 4, 5, 6}
        };
        SudokuField field = new SudokuField(incorrectArray);
    }

    @Test(expected = SudokuException.class)
    public void createSudokuFieldWithIncorrectXLowerArray() throws SudokuException {
        int[][] incorrectArray = new int[][]{
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {2, 3, 4, 5, 6, 7, 8, 9, 1},
                {5, 6, 7, 8, 9, 1, 2, 3},
                {8, 9, 1, 2, 3, 4, 5, 6, 7},
                {3, 4, 5, 6, 7, 8, 9, 1, 2},
                {6, 7, 8, 9, 1, 2, 3, 4, 5},
                {7, 8, 9, 1, 2, 3, 4, 5, 6}
        };
        SudokuField field = new SudokuField(incorrectArray);
    }

    @Test(expected = SudokuException.class)
    public void checkIncorrectVerticalLineLessBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.isVerticalLineValid(0);
    }

    @Test(expected = SudokuException.class)
    public void checkIncorrectVerticalLineLess() throws SudokuException {
        SudokuField field = new SudokuField();
        field.isVerticalLineValid(-100);
    }

    @Test(expected = SudokuException.class)
    public void checkIncorrectVerticalLineMoreBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.isVerticalLineValid(10);
    }

    @Test(expected = SudokuException.class)
    public void checkIncorrectVerticalLineMore() throws SudokuException {
        SudokuField field = new SudokuField();
        field.isVerticalLineValid(100);
    }

    @Test(expected = SudokuException.class)
    public void checkIncorrectHorizontalLineLessBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.isHorizontalLineValid(0);
    }

    @Test(expected = SudokuException.class)
    public void checkIncorrectHorizontalLineLess() throws SudokuException {
        SudokuField field = new SudokuField();
        field.isHorizontalLineValid(-100);
    }

    @Test(expected = SudokuException.class)
    public void checkIncorrectHorizontalLineMoreBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.isHorizontalLineValid(10);
    }

    @Test(expected = SudokuException.class)
    public void checkIncorrectHorizontalLineMore() throws SudokuException {
        SudokuField field = new SudokuField();
        field.isHorizontalLineValid(100);
    }

    @Test(expected = SudokuException.class)
    public void checkIncorrectSquareLessBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.isSquareValid(0);
    }

    @Test(expected = SudokuException.class)
    public void checkIncorrectSquareLess() throws SudokuException {
        SudokuField field = new SudokuField();
        field.isSquareValid(-100);
    }

    @Test(expected = SudokuException.class)
    public void checkIncorrectSquareMoreBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.isSquareValid(10);
    }

    @Test(expected = SudokuException.class)
    public void checkIncorrectSquareMore() throws SudokuException {
        SudokuField field = new SudokuField();
        field.isSquareValid(100);
    }


    @Test(expected = SudokuException.class)
    public void setIncorrectXPointLessBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.setPoint(0, 5, 1);
    }

    @Test(expected = SudokuException.class)
    public void setIncorrectXPointMoreBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.setPoint(10, 5, 1);
    }

    @Test(expected = SudokuException.class)
    public void setIncorrectXPointLess() throws SudokuException {
        SudokuField field = new SudokuField();
        field.setPoint(-10, 5, 1);
    }

    @Test(expected = SudokuException.class)
    public void setIncorrectXPointMore() throws SudokuException {
        SudokuField field = new SudokuField();
        field.setPoint(100, 5, 1);
    }

    @Test(expected = SudokuException.class)
    public void setIncorrectYPointLessBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.setPoint(5, 0, 1);
    }

    @Test(expected = SudokuException.class)
    public void setIncorrectYPointMoreBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.setPoint(5, 10, 1);
    }

    @Test(expected = SudokuException.class)
    public void setIncorrectYPointLess() throws SudokuException {
        SudokuField field = new SudokuField();
        field.setPoint(5, -10, 1);
    }

    @Test(expected = SudokuException.class)
    public void setIncorrectYPointMore() throws SudokuException {
        SudokuField field = new SudokuField();
        field.setPoint(5, -10, 1);
    }

    @Test(expected = SudokuException.class)
    public void setIncorrectValuePointLessBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.setPoint(5, 5, -1);
    }

    @Test(expected = SudokuException.class)
    public void setIncorrectValuePointMoreBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.setPoint(5, 5, 10);
    }

    @Test(expected = SudokuException.class)
    public void setIncorrectValuePointLess() throws SudokuException {
        SudokuField field = new SudokuField();
        field.setPoint(5, 5, -10);
    }

    @Test(expected = SudokuException.class)
    public void setIncorrectValuePointMore() throws SudokuException {
        SudokuField field = new SudokuField();
        field.setPoint(5, 5, 100);
    }

    @Test(expected = SudokuException.class)
    public void getIncorrectAvailableValuesXPointLessBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.getAvailableValuesForPoint(0, 5);
    }

    @Test(expected = SudokuException.class)
    public void getIncorrectAvailableValuesXPointMoreBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.getAvailableValuesForPoint(10, 5);
    }

    @Test(expected = SudokuException.class)
    public void getIncorrectAvailableValuesXPointLess() throws SudokuException {
        SudokuField field = new SudokuField();
        field.getAvailableValuesForPoint(-10, 5);
    }

    @Test(expected = SudokuException.class)
    public void getIncorrectAvailableValuesXPointMore() throws SudokuException {
        SudokuField field = new SudokuField();
        field.getAvailableValuesForPoint(100, 5);
    }

    @Test(expected = SudokuException.class)
    public void getIncorrectAvailableValuesYPointLessBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.getAvailableValuesForPoint(5, 0);
    }

    @Test(expected = SudokuException.class)
    public void getIncorrectAvailableValuesYPointMoreBorder() throws SudokuException {
        SudokuField field = new SudokuField();
        field.getAvailableValuesForPoint(5, 10);
    }

    @Test(expected = SudokuException.class)
    public void getIncorrectAvailableValuesYPointLess() throws SudokuException {
        SudokuField field = new SudokuField();
        field.getAvailableValuesForPoint(5, -10);
    }

    @Test(expected = SudokuException.class)
    public void getIncorrectAvailableValuesYPointMore() throws SudokuException {
        SudokuField field = new SudokuField();
        field.getAvailableValuesForPoint(5, -10);
    }
}
