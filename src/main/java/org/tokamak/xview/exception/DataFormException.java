package org.tokamak.xview.exception;

public class DataFormException extends Exception {

    public DataFormException() {
        super();
    }

    @Override
    public String toString() {
        return "Does not support this data format.";
    }
}
