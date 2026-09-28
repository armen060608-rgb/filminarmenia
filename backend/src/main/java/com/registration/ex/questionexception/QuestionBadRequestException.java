package com.registration.ex.questionexception;

import com.registration.ex.BadRequestException;

public class QuestionBadRequestException extends BadRequestException {
    public QuestionBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
