package com.artiomtb.sudokuresolver.test;

import com.artiomtb.sudokuresolver.SudokuField;
import com.artiomtb.sudokuresolver.SudokuResolver;
import com.artiomtb.sudokuresolver.exceptions.SudokuException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SudokuResolverTest {

    private int[][] resolvedSudokuField = new int[][]{
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
    private int[][] oneResolutionField = {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 4, 5, 6, 7, 8, 9, 1},
            {5, 6, 7, 8, 9, 1, 2, 3, 4},
            {8, 9, 1, 2, 3, 4, 5, 6, 7},
            {3, 4, 5, 6, 7, 8, 0, 0, 0},
            {6, 7, 8, 9, 1, 2, 0, 0, 0},
            {9, 1, 2, 3, 4, 5, 0, 0, 0}
    };
    private int[][] severalResolutionField = {
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
    private int[][] incorrectSudokuFieldFull = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 4, 5, 6, 7, 8, 9, 1},
            {5, 6, 7, 8, 9, 1, 2, 3, 4},
            {8, 9, 1, 2, 3, 4, 5, 6, 7},
            {3, 4, 5, 6, 7, 8, 9, 1, 2},
            {6, 7, 8, 9, 1, 2, 3, 4, 5},
            {9, 1, 2, 3, 4, 5, 6, 7, 7}
    };
    private int[][] incorrectSudokuFieldNotFull = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {2, 3, 4, 5, 6, 7, 8, 9, 9},
            {5, 6, 7, 8, 0, 0, 0, 0, 0},
            {8, 9, 1, 2, 0, 0, 0, 0, 0},
            {3, 4, 5, 6, 0, 0, 0, 0, 0},
            {6, 7, 8, 9, 0, 0, 0, 0, 0},
            {9, 1, 2, 3, 0, 0, 0, 0, 0}
    };

    @Test
    public void resolveSudokuWithOneSolution() throws SudokuException {
        String resolved = "123456789456789123789123456234567891567891234891234567345678912678912345912345678";

        SudokuField oneResolutionField = new SudokuField(this.oneResolutionField);
        SudokuResolver resolver = new SudokuResolver(oneResolutionField);
        List<SudokuField> resolvedSudoku = resolver.getResolvedSudoku();
        assertEquals(1, resolvedSudoku.size());
        assertEquals(resolvedSudoku.get(0).toEasyString(), resolved);
    }

    @Test
    public void resolveSudokuWithSeveralSolutions() throws SudokuException {
        List<String> resolved = new ArrayList<>();
        resolved.add("123456789456789123789123456234567891567891234891234567345612978678945312912378645");
        resolved.add("123456789456789123789123456234567891567891234891234567345672918678915342912348675");
        resolved.add("123456789456789123789123456234567891567891342891234675345672918678915234912348567");
        resolved.add("123456789456789123789123456234567891567891234891234567345618972678942315912375648");
        resolved.add("123456789456789123789123456234567891567891342891234567345672918678915234912348675");
        resolved.add("123456789456789123789123456234567891567891234891234567345678912678912345912345678");
        resolved.add("123456789456789123789123456234567891567891234891234675345672918678915342912348567");
        SudokuResolver resolver = new SudokuResolver(new SudokuField(severalResolutionField), Integer.MAX_VALUE);
        int resolutionsCount = 7;
        List<SudokuField> resolvedSudoku = resolver.getResolvedSudoku();
        assertEquals(resolutionsCount, resolvedSudoku.size());
        for (SudokuField field : resolvedSudoku) {
            String currentField = field.toEasyString();
            assertTrue(resolved.contains(currentField));
            resolved.remove(currentField);
        }
        assertEquals(0, resolved.size());
    }

    @Test
    public void getResolvedSudokuCountTest() throws SudokuException {
        SudokuResolver resolver = new SudokuResolver(new SudokuField(oneResolutionField));
        assertEquals(1, resolver.getResolvedSudokuCount());
        resolver = new SudokuResolver(new SudokuField(oneResolutionField), Integer.MAX_VALUE);
        assertEquals(1, resolver.getResolvedSudokuCount());
        resolver = new SudokuResolver(new SudokuField(severalResolutionField));
        assertEquals(1, resolver.getResolvedSudokuCount());
        resolver = new SudokuResolver(new SudokuField(severalResolutionField), 5);
        assertEquals(5, resolver.getResolvedSudokuCount());
        resolver = new SudokuResolver(new SudokuField(severalResolutionField), Integer.MAX_VALUE);
        assertEquals(7, resolver.getResolvedSudokuCount());
        resolver = new SudokuResolver(new SudokuField(resolvedSudokuField), Integer.MAX_VALUE);
        assertEquals(1, resolver.getResolvedSudokuCount());
    }

    @Test(expected = SudokuException.class)
    public void tryToResolveIncorrectFullField() throws SudokuException {
        SudokuResolver resolver = new SudokuResolver(new SudokuField(incorrectSudokuFieldFull));
    }

    @Test(expected = SudokuException.class)
    public void tryToResolveIncorrectNotFullField() throws SudokuException {
        SudokuResolver resolver = new SudokuResolver(new SudokuField(incorrectSudokuFieldNotFull));
    }
}
