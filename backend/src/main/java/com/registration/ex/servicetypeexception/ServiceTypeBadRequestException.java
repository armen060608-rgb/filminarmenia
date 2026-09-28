package com.registration.ex.servicetypeexception;

import com.registration.ex.BadRequestException;

public class ServiceTypeBadRequestException extends BadRequestException {
    public ServiceTypeBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
