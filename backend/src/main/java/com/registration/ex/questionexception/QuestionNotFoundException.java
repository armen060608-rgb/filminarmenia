package com.registration.ex.questionexception;

import com.registration.ex.EntityNotFoundException;

public class QuestionNotFoundException extends EntityNotFoundException {
    public QuestionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
