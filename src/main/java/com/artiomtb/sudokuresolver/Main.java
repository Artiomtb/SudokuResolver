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

            int[][] middleSudokuArray = new int[][]{
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

            int[][] easySudokuArray = new int[][]{
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

            SudokuField field = new SudokuField(easySudokuArray);
            long startTime = System.currentTimeMillis();
            SudokuResolver sudokuResolver = new SudokuResolver(field, 1000);
            long elapsedTime = (System.currentTimeMillis() - startTime);
            List<SudokuField> resolvedSudokuList = sudokuResolver.getResolvedSudoku();
            int sudokuResolvedIndex = 0;
            for (SudokuField resolvedField : resolvedSudokuList) {
                LOG.info("Resolution #" + ++sudokuResolvedIndex + ":\n" + resolvedField);
            }
            LOG.info("Found " + sudokuResolver.getResolvedSudokuCount() + " resolutions in " +
                    elapsedTime + " ms.");

            field = new SudokuField(middleSudokuArray);
            startTime = System.currentTimeMillis();
            sudokuResolver = new SudokuResolver(field, 1000);
            elapsedTime = (System.currentTimeMillis() - startTime);
            resolvedSudokuList = sudokuResolver.getResolvedSudoku();
            sudokuResolvedIndex = 0;
            for (SudokuField resolvedField : resolvedSudokuList) {
                LOG.info("Resolution #" + ++sudokuResolvedIndex + ":\n" + resolvedField);
            }
            LOG.info("Found " + sudokuResolver.getResolvedSudokuCount() + " resolutions in " +
                    elapsedTime + " ms.");

            field = new SudokuField(theHardestSudokuEver);
            startTime = System.currentTimeMillis();
            sudokuResolver = new SudokuResolver(field);
            elapsedTime = (System.currentTimeMillis() - startTime);
            resolvedSudokuList = sudokuResolver.getResolvedSudoku();
            LOG.info("Found first resolution in " + elapsedTime + " ms.");
            LOG.info("Resolution:\n" + resolvedSudokuList.get(0));
        } catch (SudokuException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
