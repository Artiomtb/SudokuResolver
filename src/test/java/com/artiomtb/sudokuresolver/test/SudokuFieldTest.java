package com.artiomtb.sudokuresolver.test;

import com.artiomtb.sudokuresolver.SudokuField;
import com.artiomtb.sudokuresolver.SudokuPoint;
import com.artiomtb.sudokuresolver.exceptions.IncorrectSudokuPointException;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SudokuFieldTest {

    private final int correctPosX[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final int correctPosY[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final int correctValue[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    public void createEmptySudokuField() throws IncorrectSudokuPointException {
        SudokuField sudokuField = new SudokuField();
        for (int y = 1; y <= 9; y++) {
            for (int x = 1; x <= 9; x++) {
                SudokuPoint point = sudokuField.getPoint(x, y);
                assertEquals(point.getPosX(), x);
                assertEquals(point.getPosY(), y);
                assertEquals(point.getValue(), 0);
            }
        }
    }

    @Test
    public void createNonEmptySudokuField() throws IncorrectSudokuPointException {
        int[][] pointsConfig = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2,},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 2, 1,},
                {1, 2, 3, 4, 5, 6, 7, 8, 9}
        };

        List<SudokuPoint> points = new ArrayList<>();

        for (int currentX : correctPosX) {
            for (int currentY : correctPosY) {
                int value = Math.max(currentX, currentY);
                points.add(new SudokuPoint(currentX, currentY, value));
            }
        }
        SudokuField sudokuField = new SudokuField(points);

        for (int currentX : correctPosX) {
            for (int currentY : correctPosY) {
                int value = Math.max(currentX, currentY);
                assertEquals(sudokuField.getPoint(currentX, currentY), new SudokuPoint(currentX, currentY, value));
            }
        }

    }
}
