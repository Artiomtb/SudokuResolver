package com.artiomtb.sudokuresolver;

import com.artiomtb.sudokuresolver.exceptions.IncorrectSudokuPointException;
import org.apache.log4j.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            SudokuField field = new SudokuField();
            LOG.info(field.checkFieldValidity());
        } catch (IncorrectSudokuPointException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
