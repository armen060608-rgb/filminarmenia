package com.registration.ex.regionexception;

import com.registration.ex.ResourceAlreadyExistException;

public class RegionAlreadyExistsException extends ResourceAlreadyExistException {
    public RegionAlreadyExistsException(String message) {
        super(message);
    }
}