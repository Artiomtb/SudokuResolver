package com.artiomtb.sudokuresolver;

import com.artiomtb.sudokuresolver.exceptions.SudokuException;
import org.apache.log4j.Logger;

public class SudokuPoint implements Cloneable {

    private int posX;
    private int posY;
    private int value;

    private static final int MIN_POSITION_X = 1;
    private static final int MIN_POSITION_Y = 1;
    private static final int MAX_POSITION_X = 9;
    private static final int MAX_POSITION_Y = 9;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 9;

    private static final Logger LOG = Logger.getLogger(SudokuPoint.class);

    public SudokuPoint(int posX, int posY, int value) throws SudokuException {
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

    public void setPosX(int posX) throws SudokuException {
        if (isValueInRange(posX, MIN_POSITION_X, MAX_POSITION_X)) {
            this.posX = posX;
        } else {
            throw new SudokuException("X position should be in range [" + MIN_POSITION_X +
                    "," + MAX_POSITION_X + "] (now " + posX + ")");
        }
    }

    public void setPosY(int posY) throws SudokuException {
        if (isValueInRange(posY, MIN_POSITION_Y, MAX_POSITION_Y)) {
            this.posY = posY;
        } else {
            throw new SudokuException("Y position should be in range [" + MIN_POSITION_Y +
                    "," + MAX_POSITION_Y + "] (now " + posY + ")");
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) throws SudokuException {
        if (isValueInRange(value, MIN_VALUE, MAX_VALUE)) {
            this.value = value;
        } else {
            throw new SudokuException("Point value should be in range [" + MIN_VALUE +
                    "," + MAX_VALUE + "] (now " + value + ")");
        }
    }

    public boolean isEmpty() {
        return this.value == 0;
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
    public SudokuPoint clone() {
        SudokuPoint clonedResult = null;
        try {
            clonedResult = (SudokuPoint) super.clone();
        } catch (CloneNotSupportedException e) {
            LOG.error("Exception while cloning " + this, e);
        }
        return clonedResult;
    }
}
