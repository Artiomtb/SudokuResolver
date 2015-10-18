package com.artiomtb.sudokuresolver.exceptions;

public class IncorrectSudokuPointException extends Exception {

    public IncorrectSudokuPointException() {
        super();
    }

    public IncorrectSudokuPointException(String message) {
        super(message);
    }

    public IncorrectSudokuPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectSudokuPointException(Throwable cause) {
        super(cause);
    }

    protected IncorrectSudokuPointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
