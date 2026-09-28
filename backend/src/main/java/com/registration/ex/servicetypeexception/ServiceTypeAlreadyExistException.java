package com.registration.ex.servicetypeexception;

import com.registration.ex.ResourceAlreadyExistException;

public class ServiceTypeAlreadyExistException extends ResourceAlreadyExistException {
    public ServiceTypeAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
