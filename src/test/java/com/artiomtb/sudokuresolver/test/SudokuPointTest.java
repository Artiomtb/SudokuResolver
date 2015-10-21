package com.artiomtb.sudokuresolver.test;

import com.artiomtb.sudokuresolver.SudokuPoint;
import com.artiomtb.sudokuresolver.exceptions.SudokuException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SudokuPointTest {

    private final int correctPosX[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final int correctPosY[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final int correctValue[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    public void createCorrectPointTest() throws SudokuException {
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
    public void setterXTest() throws SudokuException {
        SudokuPoint point = new SudokuPoint(1, 1, 1);
        for (int currentPosX : correctPosX) {
            point.setPosX(currentPosX);
            assertEquals(point.getPosX(), currentPosX);
        }
    }

    @Test
    public void setterYTest() throws SudokuException {
        SudokuPoint point = new SudokuPoint(1, 1, 1);
        for (int currentPosY : correctPosY) {
            point.setPosY(currentPosY);
            assertEquals(point.getPosY(), currentPosY);
        }
    }

    @Test
    public void setterValueTest() throws SudokuException {
        SudokuPoint point = new SudokuPoint(1, 1, 1);
        for (int currentValue : correctValue) {
            point.setValue(currentValue);
            assertEquals(point.getValue(), currentValue);
        }
    }

    @Test
    public void cloneTest() throws SudokuException, CloneNotSupportedException {
        SudokuPoint point = new SudokuPoint(1, 1, 1);
        SudokuPoint clonedPoint = point.clone();
        assertEquals(point, clonedPoint);
    }

    @Test
    public void equalsTest() throws SudokuException {
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
                assertNotEquals(point1, point2);
            }
            for (int j = i + 1; j < size; j++) {
                SudokuPoint point2 = sudokuPoints[j];
                assertNotEquals(point1, point2);
            }
            assertEquals(point1, point1.clone());
        }
    }

    @Test
    public void isEmptyTest() throws SudokuException {
        for (int currentX : correctPosX) {
            for (int currentY : correctPosY) {
                SudokuPoint point = new SudokuPoint(currentX, currentY, 0);
                assertTrue(point.isEmpty());
                for (int currentValue = 1; currentValue < correctValue.length; currentValue++) {
                    point.setValue(0);
                    assertTrue(point.isEmpty());
                    point.setValue(currentValue);
                    assertFalse(point.isEmpty());
                }
            }
        }
    }

    @Test(expected = SudokuException.class)
    public void createIncorrectXPointLessBorder() throws SudokuException {
        new SudokuPoint(0, 5, 1);
    }

    @Test(expected = SudokuException.class)
    public void createIncorrectXPointMoreBorder() throws SudokuException {
        new SudokuPoint(10, 5, 1);
    }

    @Test(expected = SudokuException.class)
    public void createIncorrectXPointLess() throws SudokuException {
        new SudokuPoint(-10, 5, 1);
    }

    @Test(expected = SudokuException.class)
    public void createIncorrectXPointMore() throws SudokuException {
        new SudokuPoint(100, 5, 1);
    }

    @Test(expected = SudokuException.class)
    public void createIncorrectYPointLessBorder() throws SudokuException {
        new SudokuPoint(5, 0, 1);
    }

    @Test(expected = SudokuException.class)
    public void createIncorrectYPointMoreBorder() throws SudokuException {
        new SudokuPoint(5, 10, 1);
    }

    @Test(expected = SudokuException.class)
    public void createIncorrectYPointLess() throws SudokuException {
        new SudokuPoint(5, -10, 1);
    }

    @Test(expected = SudokuException.class)
    public void createIncorrectYPointMore() throws SudokuException {
        new SudokuPoint(5, -10, 1);
    }

    @Test(expected = SudokuException.class)
    public void createIncorrectValuePointLessBorder() throws SudokuException {
        new SudokuPoint(5, 5, -1);
    }

    @Test(expected = SudokuException.class)
    public void createIncorrectValuePointMoreBorder() throws SudokuException {
        new SudokuPoint(5, 5, 10);
    }

    @Test(expected = SudokuException.class)
    public void createIncorrectValuePointLess() throws SudokuException {
        new SudokuPoint(5, 5, -10);
    }

    @Test(expected = SudokuException.class)
    public void createIncorrectValuePointMore() throws SudokuException {
        new SudokuPoint(5, 5, 100);
    }


}
