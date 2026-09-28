package com.registration.ex.questionexception;

import com.registration.ex.ResourceAlreadyExistException;

public class QuestionAlreadyExistException extends ResourceAlreadyExistException {
    public QuestionAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
