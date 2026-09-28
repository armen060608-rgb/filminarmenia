package com.registration.ex.serviceexception;

import com.registration.ex.BadRequestException;

public class ServiceBadRequestException extends BadRequestException {
    public ServiceBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
