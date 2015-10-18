package com.artiomtb.sudokuresolver;

import com.artiomtb.sudokuresolver.exceptions.IncorrectSudokuFieldLineNumberException;
import com.artiomtb.sudokuresolver.exceptions.IncorrectSudokuPointException;
import org.apache.log4j.Logger;

import java.util.BitSet;

public class SudokuField {

    private SudokuPoint[][] field = new SudokuPoint[9][9];

    private static final Logger LOG = Logger.getLogger(SudokuField.class);

    public SudokuField() throws IncorrectSudokuPointException {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                field[x][y] = new SudokuPoint(x + 1, y + 1, y + 1);
            }
        }
        field[4][0] = new SudokuPoint(1, 5, 2);
        LOG.debug("Created: " + toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Field:\n");
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                sb.append(field[x][y].getValue() + " ");
                if (x == 2 || x == 5) {
                    sb.append("| ");
                }
            }
            sb.append("\n");
            if (y == 2 || y == 5) {
                sb.append("---------------------\n");
            }
        }
        return sb.toString();
    }

    public boolean checkVerticalLine(int lineNum) throws IncorrectSudokuFieldLineNumberException {
        if (lineNum < 1 || lineNum > 9)
            throw new IncorrectSudokuFieldLineNumberException("Line value should be in range [1,9] (now " + lineNum + ")");
        SudokuPoint[] line = this.field[lineNum - 1];
        return checkArrayToUnique(line);
    }

    public boolean checkHorizontalLine(int lineNum) throws IncorrectSudokuFieldLineNumberException {
        if (lineNum < 1 || lineNum > 9)
            throw new IncorrectSudokuFieldLineNumberException("Line value should be in range [1,9] (now " + lineNum + ")");
        SudokuPoint[] line = new SudokuPoint[9];
        for (int i = 0; i < 9; i++) {
            line[i] = field[i][lineNum - 1];
        }
        return checkArrayToUnique(line);
    }

    public boolean checkSquare(int squareNum) throws IncorrectSudokuFieldLineNumberException {
        if (squareNum < 1 || squareNum > 9)
            throw new IncorrectSudokuFieldLineNumberException("Square value should be in range [1,9] (now " + squareNum + ")");
        SudokuPoint[] line = new SudokuPoint[9];
        int x;
        int y;
        int mod = squareNum % 3;
        if (mod == 0) {
            x = 6;
        } else if (mod == 1) {
            x = 0;
        } else {
            x = 3;
        }
        if (squareNum <= 3) {
            y = 0;
        } else if (squareNum <= 6) {
            y = 3;
        } else {
            y = 6;
        }
        int lineIndex = 0;
        for (int i = y; i < y + 3; i++) {
            for (int j = x; j < x + 3; j++) {
                line[lineIndex++] = field[j][i];
            }
        }
        return checkArrayToUnique(line);
    }

    public boolean checkFieldValidity() {
        boolean result = true;
        try {
            for (int i = 1; i <= 9; i++) {
                if (!checkVerticalLine(i) || !checkHorizontalLine(i) || !checkSquare(i)) {
                    result = false;
                    break;
                }
            }
        } catch (IncorrectSudokuFieldLineNumberException e) {
            LOG.error("Exception while checking field validity", e);
        }
        return result;
    }

    private boolean checkArrayToUnique(SudokuPoint[] array) {
        StringBuilder sb = new StringBuilder();
        for (SudokuPoint point : array) {
            sb.append(point.getValue() + " ");
        }
        LOG.debug("Checking array: " + sb.toString());
        boolean isArrayUnique = true;
        BitSet bits = new BitSet(9);
        for (int i = 0; i < 9; i++) {
            int currentValue = array[i].getValue();
            if (currentValue > 0) {
                if (bits.get(currentValue)) {
                    isArrayUnique = false;
                    break;
                } else {
                    bits.set(currentValue);
                }
            }
        }
        return isArrayUnique;
    }
}
