package com.artiomtb.sudokuresolver;

import com.artiomtb.sudokuresolver.exceptions.IncorrectSudokuPointException;
import org.apache.log4j.Logger;

public class SudokuPoint implements Cloneable {

    private int posX;
    private int posY;
    private int value;

    private static final int MIN_POSITION_X = 1;
    private static final int MIN_POSITION_Y = 1;
    private static final int MAX_POSITION_X = 9;
    private static final int MAX_POSITION_Y = 9;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 9;

    private static final Logger LOG = Logger.getLogger(SudokuPoint.class);

    public SudokuPoint(int posX, int posY, int value) throws IncorrectSudokuPointException {
        setPosX(posX);
        setPosY(posY);
        setValue(value);
        LOG.debug("Created " + this.toString());
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) throws IncorrectSudokuPointException {
        if (isValueInRange(posX, MIN_POSITION_X, MAX_POSITION_X)) {
            this.posX = posX;
        } else {
            throw new IncorrectSudokuPointException("X position should be in range [" + MIN_POSITION_X +
                    "," + MAX_POSITION_X + "] (now " + posX + ")");
        }
    }

    public void setPosY(int posY) throws IncorrectSudokuPointException {
        if (isValueInRange(posY, MIN_POSITION_Y, MAX_POSITION_Y)) {
            this.posY = posY;
        } else {
            throw new IncorrectSudokuPointException("Y position should be in range [" + MIN_POSITION_Y +
                    "," + MAX_POSITION_Y + "] (now " + posY + ")");
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) throws IncorrectSudokuPointException {
        if (isValueInRange(value, MIN_VALUE, MAX_VALUE)) {
            this.value = value;
        } else {
            throw new IncorrectSudokuPointException("Point value should be in range [" + MIN_VALUE +
                    "," + MAX_VALUE + "] (now " + value + ")");
        }
    }

    private boolean isValueInRange(int value, int minValue, int maxValue) {
        return value >= minValue && value <= maxValue;
    }

    @Override
    public String toString() {
        return "Point [" + posX + "," + posY + "] = " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuPoint anotherPoint = (SudokuPoint) o;
        return posX == anotherPoint.posX && posY == anotherPoint.posY && value == anotherPoint.value;
    }

    @Override
    public int hashCode() {
        int result = posX;
        result = 31 * result + posY;
        result = 31 * result + value;
        return result;
    }

    @Override
    public SudokuPoint clone() throws CloneNotSupportedException {
        return (SudokuPoint) super.clone();
    }
}
