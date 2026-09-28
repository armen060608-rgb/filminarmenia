package com.registration.ex.serviceexception;

import com.registration.ex.ResourceAlreadyExistException;

public class ServiceAlreadyExistException extends ResourceAlreadyExistException {
    public ServiceAlreadyExistException(String errorMessage) {
        super(errorMessage);
    }
}
