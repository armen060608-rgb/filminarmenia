package com.registration.ex.regionexception;

import com.registration.ex.EntityNotFoundException;

public class RegionNotFoundException extends EntityNotFoundException {
    public RegionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

