package com.artiomtb.sudokuresolver;

import com.artiomtb.sudokuresolver.exceptions.SudokuException;
import org.apache.log4j.Logger;

import java.util.List;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        try {

            int[][] theHardestSudokuEver = new int[][]{
                    {8, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 3, 6, 0, 0, 0, 0, 0},
                    {0, 7, 0, 0, 9, 0, 2, 0, 0},
                    {0, 5, 0, 0, 0, 7, 0, 0, 0},
                    {0, 0, 0, 0, 4, 5, 7, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0, 3, 0},
                    {0, 0, 1, 0, 0, 0, 0, 6, 8},
                    {0, 0, 8, 5, 0, 0, 0, 1, 0},
                    {0, 9, 0, 0, 0, 0, 4, 0, 0}
            };

            SudokuField field = new SudokuField(theHardestSudokuEver);
            SudokuResolver sudokuResolver = new SudokuResolver(field);
            int sudokuResolvedIndex = 0;
            List<SudokuField> resolvedSudokuList = sudokuResolver.getResolvedSudoku();
            for (SudokuField resolvedField : resolvedSudokuList) {
                LOG.info("Resolution #" + ++sudokuResolvedIndex + "\n" + resolvedField);
            }
        } catch (SudokuException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
