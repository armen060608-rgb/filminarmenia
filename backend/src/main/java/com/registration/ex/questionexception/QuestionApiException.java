package com.registration.ex.questionexception;

import com.registration.ex.ApiException;

public class QuestionApiException extends ApiException {
    public QuestionApiException(String errorMessage) {
        super(errorMessage);
    }

    public QuestionApiException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
