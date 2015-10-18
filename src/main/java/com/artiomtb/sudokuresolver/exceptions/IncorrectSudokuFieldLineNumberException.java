package com.artiomtb.sudokuresolver.exceptions;

public class IncorrectSudokuFieldLineNumberException extends Exception {
    public IncorrectSudokuFieldLineNumberException() {
        super();
    }

    public IncorrectSudokuFieldLineNumberException(String message) {
        super(message);
    }

    public IncorrectSudokuFieldLineNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectSudokuFieldLineNumberException(Throwable cause) {
        super(cause);
    }

    protected IncorrectSudokuFieldLineNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
