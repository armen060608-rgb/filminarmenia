package com.registration.ex.faqexception;

import com.registration.ex.ApiException;

public class FaqApiException extends ApiException {
    public FaqApiException(String errorMessage) {
        super(errorMessage);
    }

    public FaqApiException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
