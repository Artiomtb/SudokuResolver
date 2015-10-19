package com.artiomtb.sudokuresolver;

import com.artiomtb.sudokuresolver.exceptions.IncorrectSudokuFieldLineNumberException;
import org.apache.log4j.Logger;

import java.util.List;

public class SudokuResolver {

    private SudokuField field;

    private static final Logger LOG = Logger.getLogger(SudokuResolver.class);

    public SudokuResolver(SudokuField field) {
        this.field = field;
        //TODO create resolver logic
    }

    private int getAvailableValuesCount(int posX, int posY) throws IncorrectSudokuFieldLineNumberException {
        return field.getAvailableValuesForPoint(posX, posY).size();
    }

    private int getAvailableValuesCount(SudokuPoint point) throws IncorrectSudokuFieldLineNumberException {
        return field.getAvailableValuesForPoint(point).size();
    }

    private SudokuPoint getPointWithMinimalAvailableValues(SudokuField field) throws IncorrectSudokuFieldLineNumberException {
        List<SudokuPoint> allEmptySudokuPoints = field.getAllEmptySudokuPoints();
        SudokuPoint tempPoint = allEmptySudokuPoints.get(0);
        int x = tempPoint.getPosX();
        int y = tempPoint.getPosY();
        int tempAvailableValues = getAvailableValuesCount(x, y);
        for (int i = 1; i < allEmptySudokuPoints.size(); i++) {
            SudokuPoint currentPoint = allEmptySudokuPoints.get(i);
            int currentAvailableVariants = getAvailableValuesCount(currentPoint);
            if (currentAvailableVariants < tempAvailableValues) {
                tempAvailableValues = currentAvailableVariants;
                x = currentPoint.getPosX();
                y = currentPoint.getPosY();
            }
        }
        return field.getPoint(x, y);
    }
}
