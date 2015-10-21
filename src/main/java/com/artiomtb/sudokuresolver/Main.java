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

            int[][] testSudokuArray = new int[][]{
                    {1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {4, 0, 0, 0, 0, 0, 0, 0, 0},
                    {7, 0, 0, 0, 0, 0, 0, 0, 0},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0},
                    {5, 0, 0, 0, 0, 0, 0, 0, 0},
                    {8, 0, 0, 0, 0, 0, 0, 0, 0},
                    {3, 0, 0, 0, 0, 0, 0, 0, 0},
                    {6, 0, 0, 0, 0, 0, 0, 0, 0},
                    {9, 0, 0, 0, 0, 0, 0, 0, 0}
            };

            int[][] correctSudokuArray = new int[][]{
                    {1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {4, 5, 6, 7, 8, 9, 1, 2, 3},
                    {7, 8, 9, 1, 2, 3, 4, 5, 6},
                    {2, 3, 4, 5, 6, 7, 8, 9, 1},
                    {5, 6, 7, 8, 0, 0, 0, 0, 0},
                    {8, 9, 1, 2, 0, 0, 0, 0, 0},
                    {3, 4, 5, 6, 0, 0, 0, 0, 0},
                    {6, 7, 8, 9, 0, 0, 0, 0, 0},
                    {9, 1, 2, 3, 0, 0, 0, 0, 0}
            };

            SudokuField field = new SudokuField(theHardestSudokuEver);
            long startTime = System.currentTimeMillis();
            SudokuResolver sudokuResolver = new SudokuResolver(field);
            List<SudokuField> resolvedSudokuList = sudokuResolver.getResolvedSudoku();
            for (SudokuField resolvedField : resolvedSudokuList) {
                LOG.info("Resolution:\n" + resolvedField);
            }
            LOG.info("Found first resolution in " +
                    (System.currentTimeMillis() - startTime) + " ms.");
            int sudokuResolvedIndex = 0;

//            sudokuResolver = new SudokuResolver(field, 100);
            resolvedSudokuList = sudokuResolver.getResolvedSudoku();
            for (SudokuField resolvedField : resolvedSudokuList) {
                LOG.info("Resolution #" + ++sudokuResolvedIndex + "\n" + resolvedField);
            }
            LOG.info("Found " + sudokuResolver.getResolvedSudokuCount() + " resolutions in " +
                    (System.currentTimeMillis() - startTime) + " ms.");
        } catch (SudokuException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
