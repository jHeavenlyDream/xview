package org.tokamak.xview.exception;

import java.io.IOException;

public class DataFormException extends IOException {

    public DataFormException() {
    }

    @Override
    public String toString() {
        return "Does not support this data format.";
    }
}
