package com.registration.ex.serviceexception;

import com.registration.ex.ApiException;

public class ServiceApiException extends ApiException {
    public ServiceApiException(String errorMessage) {
        super(errorMessage);
    }

    public ServiceApiException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
