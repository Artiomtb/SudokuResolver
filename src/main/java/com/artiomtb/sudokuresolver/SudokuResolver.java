package com.artiomtb.sudokuresolver;

import com.artiomtb.sudokuresolver.exceptions.IncorrectSudokuPointException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SudokuResolver {

    private SudokuField field;
    private List<SudokuField> answers = new ArrayList<>();
    private boolean isSolved = false;
    private int answerLimit = DEFAULT_ANSWER_LIMIT;

    private static final int DEFAULT_ANSWER_LIMIT = 1000;


    private static final Logger LOG = Logger.getLogger(SudokuResolver.class);

    public SudokuResolver(SudokuField field) throws IncorrectSudokuPointException {
        this.field = field;
        if (!field.isSolved()) {
            resolveSudoku();
        }
    }

    public SudokuResolver(SudokuField field, int limit) {
        this.field = field;
        answerLimit = limit;
        if (!field.isSolved()) {
            resolveSudoku();
        }
    }

    private void resolveSudoku() {
        LOG.info("Trying to resolve sudoku:\n" + field);
        tryToSetValue(field);
        LOG.info("Founded " + getResolvedSudokuCount() + " resolutions for this field");
    }

    public List<SudokuField> getResolvedSudoku() {
        return this.answers;
    }

    public int getResolvedSudokuCount() {
        return this.answers.size();
    }

    private int getAvailableValuesCount(SudokuPoint point) {
        return field.getAvailableValuesForPoint(point).size();
    }

    private SudokuPoint getPointWithMinimalAvailableValues(SudokuField field) {
        List<SudokuPoint> allEmptySudokuPoints = field.getAllEmptySudokuPoints();
        SudokuPoint tempPoint = allEmptySudokuPoints.get(0);
        int x = tempPoint.getPosX();
        int y = tempPoint.getPosY();
        int tempAvailableValues = getAvailableValuesCount(tempPoint);
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

    private void tryToSetValue(SudokuField currentField) {
        if (isSolved) {
            if (answers.size() >= answerLimit) {
                LOG.debug("Answers limit " + answerLimit + " exceeded. Stopping resolving");
                return;
            }
        }
        if (currentField.isSolved()) {
            isSolved = true;
            answers.add(currentField);
            LOG.debug("Found " + answers.size() + " resolution");
            return;
        } else if (!currentField.checkFieldValidity()) {
            LOG.debug("Current resolution is incorrect. Returning");
            return;
        } else {
            try {
                SudokuPoint pointWithMinAvailValues = getPointWithMinimalAvailableValues(currentField);
                List<Integer> availableValuesForPoint = currentField.getAvailableValuesForPoint(pointWithMinAvailValues);
                LOG.debug("Current point to set " + pointWithMinAvailValues + ", values: " + availableValuesForPoint);
                int i = 0;
                for (Integer currentValue : availableValuesForPoint) {
                    LOG.debug("Checking " + ++i + " variant for " + pointWithMinAvailValues + " (set " + currentValue + ")");
                    SudokuField fieldBeforeSetTry = currentField.clone();
                    fieldBeforeSetTry.setPoint(pointWithMinAvailValues.getPosX(), pointWithMinAvailValues.getPosY(), currentValue);
                    tryToSetValue(fieldBeforeSetTry);
                }
            } catch (IncorrectSudokuPointException e) {
                LOG.error("Exception while setting Point" , e);
            }
        }
    }
}
