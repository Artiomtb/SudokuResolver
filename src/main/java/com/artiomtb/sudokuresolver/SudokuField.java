package com.artiomtb.sudokuresolver;

import com.artiomtb.sudokuresolver.exceptions.IncorrectSudokuFieldLineNumberException;
import com.artiomtb.sudokuresolver.exceptions.IncorrectSudokuPointException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class SudokuField {

    private SudokuPoint[][] field = new SudokuPoint[9][9];

    private static final Logger LOG = Logger.getLogger(SudokuField.class);

    public SudokuField() throws IncorrectSudokuPointException {
        this.field = getEmptyField();
        LOG.debug("Created: " + toString());
    }

    public SudokuField(Iterable<SudokuPoint> points) throws IncorrectSudokuPointException {
        this.field = getEmptyField();
        for (SudokuPoint point : points) {
            this.field[point.getPosX() - 1][point.getPosY() - 1] = point;
        }
        LOG.debug("Created: " + toString());
    }

    private SudokuPoint[][] getEmptyField() throws IncorrectSudokuPointException {
        SudokuPoint[][] emptyField = new SudokuPoint[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                emptyField[x][y] = new SudokuPoint(x + 1, y + 1, 0);
            }
        }
        return emptyField;
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

    public SudokuPoint getPoint(int posX, int posY) {
        return this.field[posX - 1][posY - 1];
    }

    public boolean checkVerticalLine(int lineNum) throws IncorrectSudokuFieldLineNumberException {
        return checkArrayToUnique(getPointsByVerticalLine(lineNum));
    }

    public boolean checkHorizontalLine(int lineNum) throws IncorrectSudokuFieldLineNumberException {
        return checkArrayToUnique(getPointsByHorizontalLine(lineNum));
    }

    public boolean checkSquare(int squareNum) throws IncorrectSudokuFieldLineNumberException {
        return checkArrayToUnique(getPointsBySquareNum(squareNum));
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

    public void setPoint(SudokuPoint point) {
        field[point.getPosX() - 1][point.getPosY() - 1] = point;
        LOG.debug("Set filed " + point);
    }

    public void setPoint(int posX, int posY, int value) throws IncorrectSudokuPointException {
        SudokuPoint point = new SudokuPoint(posX, posY, value);
        setPoint(point);
    }

    private int getSquareByPos(int posX, int posY) {
        int squareNum;
        if (posX < 4) {
            if (posY < 4) {
                squareNum = 1;
            } else if (posY < 7) {
                squareNum = 4;
            } else {
                squareNum = 7;
            }
        } else if (posX < 7) {
            if (posY < 4) {
                squareNum = 2;
            } else if (posY < 7) {
                squareNum = 5;
            } else {
                squareNum = 8;
            }
        } else {
            if (posY < 4) {
                squareNum = 3;
            } else if (posY < 7) {
                squareNum = 6;
            } else {
                squareNum = 9;
            }
        }
        return squareNum;
    }

    private SudokuPoint[] getPointsByVerticalLine(int lineNum) throws IncorrectSudokuFieldLineNumberException {
        if (lineNum < 1 || lineNum > 9)
            throw new IncorrectSudokuFieldLineNumberException("Line value should be in range [1,9] (now " + lineNum + ")");
        return this.field[lineNum - 1];
    }

    private SudokuPoint[] getPointsByHorizontalLine(int lineNum) throws IncorrectSudokuFieldLineNumberException {
        if (lineNum < 1 || lineNum > 9)
            throw new IncorrectSudokuFieldLineNumberException("Line value should be in range [1,9] (now " + lineNum + ")");
        SudokuPoint[] line = new SudokuPoint[9];
        for (int i = 0; i < 9; i++) {
            line[i] = field[i][lineNum - 1];
        }
        return line;
    }

    private SudokuPoint[] getPointsBySquareNum(int squareNum) throws IncorrectSudokuFieldLineNumberException {
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
        return line;
    }

    public List<Integer> getAvailableValuesForPoint(int posX, int posY) throws IncorrectSudokuFieldLineNumberException {
        List<Integer> nonAvailableValuesVertical = getNonAvailableValuesForVertical(posX);
        List<Integer> nonAvailableValuesHorizontal = getNonAvailableValuesForHorizontal(posY);
        List<Integer> nonAvailableValuesSquare = getNonAvailableValuesForSquare(getSquareByPos(posX, posY));
        List<Integer> nonAvailableValues = new ArrayList<>();
        List<Integer> availableValues = new ArrayList<>();
        nonAvailableValues.addAll(nonAvailableValuesHorizontal);
        nonAvailableValues.removeAll(nonAvailableValuesVertical);
        nonAvailableValues.addAll(nonAvailableValuesVertical);
        nonAvailableValues.removeAll(nonAvailableValuesSquare);
        nonAvailableValues.addAll(nonAvailableValuesSquare);
        LOG.debug("Non available values for point " + nonAvailableValues);
        for (int i = 1; i <= 9; i++) {
            if (!nonAvailableValues.contains(i)) {
                availableValues.add(i);
            }
        }
        LOG.debug("Available values for point: " + availableValues);
        return availableValues;
    }

    public List<Integer> getAvailableValuesForPoint(SudokuPoint point) {
        List<Integer> availableValues = null;
        try {
            availableValues = getAvailableValuesForPoint(point.getPosX(), point.getPosY());
        } catch (IncorrectSudokuFieldLineNumberException e) {
            LOG.error("Exception while getting available values for " + point, e);
        }
        return availableValues;
    }

    private List<Integer> getNonAvailableValuesForVertical(int lineNum) throws IncorrectSudokuFieldLineNumberException {
        return getNonAvailableValuesForArray(getPointsByVerticalLine(lineNum));
    }

    private List<Integer> getNonAvailableValuesForHorizontal(int lineNum) throws IncorrectSudokuFieldLineNumberException {
        return getNonAvailableValuesForArray(getPointsByHorizontalLine(lineNum));
    }

    private List<Integer> getNonAvailableValuesForSquare(int squareNum) throws IncorrectSudokuFieldLineNumberException {
        return getNonAvailableValuesForArray(getPointsBySquareNum(squareNum));
    }

    private List<Integer> getNonAvailableValuesForArray(SudokuPoint[] array) {
        StringBuilder sb = new StringBuilder();
        for (SudokuPoint point : array) {
            sb.append(point.getValue() + " ");
        }
        List<Integer> nonAvailableValues = new ArrayList<>();
        LOG.debug("Getting available values for array: " + sb.toString());
        for (int i = 0; i < 9; i++) {
            int currentValue = array[i].getValue();
            if (currentValue > 0) {
                nonAvailableValues.add(currentValue);
            }
        }
        LOG.debug("Non available: " + nonAvailableValues);
        return nonAvailableValues;
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
