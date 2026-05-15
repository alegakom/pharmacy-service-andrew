package org.pharmacy.exception;

import org.pharmacy.constant.ExceptionMessageConstants;

public class DuplicateInnException extends RuntimeException {

    public DuplicateInnException(String inn) {
        super(String.format(ExceptionMessageConstants.DUPLICATE_INN, inn));
    }
}
