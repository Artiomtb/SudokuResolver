package com.artiomtb.sudokuresolver;

import com.artiomtb.sudokuresolver.exceptions.SudokuException;
import org.apache.log4j.Logger;

import java.util.*;

public class SudokuResolver {

    private SudokuField field;
    private List<SudokuField> answers = new ArrayList<>();
    private boolean isSolved = false;
    private int answerLimit = DEFAULT_ANSWER_LIMIT;

    private static final int DEFAULT_ANSWER_LIMIT = 1;


    private static final Logger LOG = Logger.getLogger(SudokuResolver.class);

    public SudokuResolver(SudokuField field) throws SudokuException {
        this.field = field;
        if (!field.checkFieldValidity()) {
            throw new SudokuException("This field is not correct");
        } else {
            resolveSudoku();
        }
    }

    public SudokuResolver(SudokuField field, int limit) throws SudokuException {
        this.field = field;
        answerLimit = limit;
        if (!field.checkFieldValidity()) {
            throw new SudokuException("This field is not correct");
        } else {
            resolveSudoku();
        }
    }

    private void resolveSudoku() {
        LOG.info("Trying to resolve sudoku:\n" + field);
        if (field.isSolved()) {
            LOG.info("The field is already solved. Returning the same");
            answers.add(field);
        } else {
            tryToSetValue(field);
            LOG.info("Founded " + getResolvedSudokuCount() + " resolutions for this field");
        }
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
        if (isSolved && answers.size() >= answerLimit) {
            LOG.debug("Answers limit " + answerLimit + " exceeded. Stopping resolving");
        } else if (currentField.isSolved()) {
            isSolved = true;
            answers.add(currentField);
            LOG.debug("Found " + answers.size() + " resolution");
        } else {
            try {
                SudokuPoint pointWithMinAvailValues = getPointWithMinimalAvailableValues(currentField);
                List<Integer> availableValuesForPoint = currentField.getAvailableValuesForPoint(pointWithMinAvailValues);

                if (availableValuesForPoint.size() > 1) {
                    availableValuesForPoint = getOptimalOrder(availableValuesForPoint, currentField, pointWithMinAvailValues);
                }

                LOG.debug("Current point to set " + pointWithMinAvailValues + ", values: " + availableValuesForPoint);
                int i = 0;
                for (Integer currentValue : availableValuesForPoint) {
                    LOG.debug("Checking " + ++i + " variant for " + pointWithMinAvailValues + " (set " + currentValue + ")");
                    SudokuField fieldBeforeSetTry = currentField.clone();
                    fieldBeforeSetTry.setPoint(pointWithMinAvailValues.getPosX(), pointWithMinAvailValues.getPosY(), currentValue);
                    tryToSetValue(fieldBeforeSetTry);
                }
            } catch (SudokuException e) {
                LOG.error("Exception while setting Point", e);
            }
        }
    }

    private List<Integer> getOptimalOrder(List<Integer> availableValuesForPoint, SudokuField currentField, SudokuPoint pointWithMinAvailValues) {
        int[] groupValueForAllAnotherPoints = new int[9];
        List<Integer> result = new ArrayList<>();
        List<SudokuPoint> anotherEmptyPoints = currentField.getAllEmptySudokuPoints();
        anotherEmptyPoints.remove(pointWithMinAvailValues);
        for (SudokuPoint point : anotherEmptyPoints) {
            List<Integer> availableValuesForCurPoint = currentField.getAvailableValuesForPoint(point);
            for (Integer currentValue : availableValuesForCurPoint) {
                groupValueForAllAnotherPoints[currentValue - 1] = groupValueForAllAnotherPoints[currentValue - 1] + 1;
            }
        }
        int[][] groupValueForAvailPointsOnly = new int[availableValuesForPoint.size()][2];
        int index = 0;
        for (Integer currentValue : availableValuesForPoint) {
            groupValueForAvailPointsOnly[index][0] = currentValue;
            groupValueForAvailPointsOnly[index++][1] = groupValueForAllAnotherPoints[currentValue - 1];
        }
        Arrays.sort(groupValueForAvailPointsOnly, (arr1, arr2) -> Integer.compare(arr1[1], arr2[1]));
        if (groupValueForAvailPointsOnly[0][1] != 0) {
            for (int[] currentValue : groupValueForAvailPointsOnly) {
                result.add(currentValue[0]);
            }
        } else if (groupValueForAvailPointsOnly.length == 1 || (groupValueForAvailPointsOnly.length > 1 && groupValueForAvailPointsOnly[1][1] != 0)) {
            result.add(groupValueForAvailPointsOnly[0][0]);
        }
        return result;
    }

}
