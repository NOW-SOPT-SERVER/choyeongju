package org.sopt.homework3.exception;

import org.sopt.homework3.common.dto.ErrorMessage;

public class CustomizedException extends BusinessException{
    public CustomizedException(ErrorMessage errorMessage){
        super(errorMessage);
    }
}
