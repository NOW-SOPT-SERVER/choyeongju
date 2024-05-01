package org.sopt.homework3.exception;

import org.sopt.homework3.common.dto.ErrorMessage;

public class NotFoundException extends BusinessException {
    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}