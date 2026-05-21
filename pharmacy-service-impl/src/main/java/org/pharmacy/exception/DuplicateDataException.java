package org.pharmacy.exception;

import org.pharmacy.constant.ExceptionMessageConstants;

public class DuplicateDataException extends RuntimeException {

    public DuplicateDataException(String inn) {
        super(String.format(ExceptionMessageConstants.DUPLICATE_DATA, inn));
    }
}
