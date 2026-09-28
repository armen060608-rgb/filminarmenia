package com.registration.ex.servicetypeexception;

import com.registration.ex.EntityNotFoundException;

public class ServiceTypeNotFoundException extends EntityNotFoundException {
    public ServiceTypeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
