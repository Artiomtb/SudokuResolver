package com.artiomtb.sudokuresolver.test;

import com.artiomtb.sudokuresolver.SudokuPoint;
import com.artiomtb.sudokuresolver.exceptions.IncorrectSudokuPointException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SudokuPointTest {

    private final int correctPosX[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final int correctPosY[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final int correctValue[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    public void createCorrectPointTest() throws IncorrectSudokuPointException {
        for (int currentX : correctPosX) {
            for (int currentY : correctPosY) {
                for (int currentValue : correctValue) {
                    SudokuPoint point = new SudokuPoint(currentX, currentY, currentValue);
                    assertEquals(point.getPosX(), currentX);
                    assertEquals(point.getPosY(), currentY);
                    assertEquals(point.getValue(), currentValue);
                }
            }
        }
    }

    @Test
    public void setterXTest() throws IncorrectSudokuPointException {
        SudokuPoint point = new SudokuPoint(1, 1, 1);
        for (int currentPosX : correctPosX) {
            point.setPosX(currentPosX);
            assertEquals(point.getPosX(), currentPosX);
        }
    }

    @Test
    public void setterYTest() throws IncorrectSudokuPointException {
        SudokuPoint point = new SudokuPoint(1, 1, 1);
        for (int currentPosY : correctPosY) {
            point.setPosY(currentPosY);
            assertEquals(point.getPosY(), currentPosY);
        }
    }

    @Test
    public void setterValueTest() throws IncorrectSudokuPointException {
        SudokuPoint point = new SudokuPoint(1, 1, 1);
        for (int currentValue : correctValue) {
            point.setValue(currentValue);
            assertEquals(point.getValue(), currentValue);
        }
    }

    @Test
    public void cloneTest() throws IncorrectSudokuPointException, CloneNotSupportedException {
        SudokuPoint point = new SudokuPoint(1,1,1);
        SudokuPoint clonedPoint = point.clone();
        assertEquals(point, clonedPoint);
    }

    @Test
    public void equalsTest() throws IncorrectSudokuPointException {
        List<SudokuPoint> allAvailablePoints = new ArrayList<>();
        for (int currentPosX : correctPosX) {
            for (int currentPosY : correctPosY) {
                for (int currentValue : correctValue) {
                    allAvailablePoints.add(new SudokuPoint(currentPosX, currentPosY, currentValue));
                }
            }
        }
        int size = allAvailablePoints.size();
        SudokuPoint[] sudokuPoints = allAvailablePoints.toArray(new SudokuPoint[size]);
        for (int i = 0; i < size; i++) {
            SudokuPoint point1 = sudokuPoints[i];
            for (int j = 0; j < i; j++) {
                SudokuPoint point2 = sudokuPoints[j];
                assertFalse(point1.equals(point2));
                assertFalse(point2.equals(point1));
            }
            for (int j = i + 1; j < size; j++) {
                SudokuPoint point2 = sudokuPoints[j];
                assertFalse(point1.equals(point2));
                assertFalse(point2.equals(point1));
            }
            SudokuPoint point2 = new SudokuPoint(point1.getPosX(), point1.getPosY(), point1.getValue());
            assertTrue(point1.equals(point2));
            assertTrue(point2.equals(point1));
        }
    }

    @Test(expected = IncorrectSudokuPointException.class)
    public void createIncorrectXPointLessBorder() throws IncorrectSudokuPointException {
        new SudokuPoint(0, 5, 1);
    }

    @Test(expected = IncorrectSudokuPointException.class)
    public void createIncorrectXPointMoreBorder() throws IncorrectSudokuPointException {
        new SudokuPoint(10, 5, 1);
    }

    @Test(expected = IncorrectSudokuPointException.class)
    public void createIncorrectXPointLess() throws IncorrectSudokuPointException {
        new SudokuPoint(-10, 5, 1);
    }

    @Test(expected = IncorrectSudokuPointException.class)
    public void createIncorrectXPointMore() throws IncorrectSudokuPointException {
        new SudokuPoint(100, 5, 1);
    }

    @Test(expected = IncorrectSudokuPointException.class)
    public void createIncorrectYPointLessBorder() throws IncorrectSudokuPointException {
        new SudokuPoint(5, 0, 1);
    }

    @Test(expected = IncorrectSudokuPointException.class)
    public void createIncorrectYPointMoreBorder() throws IncorrectSudokuPointException {
        new SudokuPoint(5, 10, 1);
    }

    @Test(expected = IncorrectSudokuPointException.class)
    public void createIncorrectYPointLess() throws IncorrectSudokuPointException {
        new SudokuPoint(5, -10, 1);
    }

    @Test(expected = IncorrectSudokuPointException.class)
    public void createIncorrectYPointMore() throws IncorrectSudokuPointException {
        new SudokuPoint(5, -10, 1);
    }

    @Test(expected = IncorrectSudokuPointException.class)
    public void createIncorrectValuePointLessBorder() throws IncorrectSudokuPointException {
        new SudokuPoint(5, 5, 0);
    }

    @Test(expected = IncorrectSudokuPointException.class)
    public void createIncorrectValuePointMoreBorder() throws IncorrectSudokuPointException {
        new SudokuPoint(5, 5, 10);
    }

    @Test(expected = IncorrectSudokuPointException.class)
    public void createIncorrectValuePointLess() throws IncorrectSudokuPointException {
        new SudokuPoint(5, 5, -10);
    }

    @Test(expected = IncorrectSudokuPointException.class)
    public void createIncorrectValuePointMore() throws IncorrectSudokuPointException {
        new SudokuPoint(5, 5, 100);
    }


}
