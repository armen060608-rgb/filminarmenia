package com.registration.ex.servicetypeexception;

import com.registration.ex.ApiException;

public class ServiceTypeApiException extends ApiException {
    public ServiceTypeApiException(String errorMessage) {
        super(errorMessage);
    }

    public ServiceTypeApiException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
