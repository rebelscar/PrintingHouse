package com.citb408.printing.abstraction;

public class OutOfPaperException extends Exception {
    public OutOfPaperException(String message) {
        super(message);
    }
}
