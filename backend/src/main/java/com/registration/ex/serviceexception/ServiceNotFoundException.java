package com.registration.ex.serviceexception;

import com.registration.ex.EntityNotFoundException;

public class ServiceNotFoundException extends EntityNotFoundException {
    public ServiceNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
