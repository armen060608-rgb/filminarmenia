package com.registration.ex.regionexception;

import com.registration.ex.BadRequestException;

public class RegionBadRequestException extends BadRequestException {
    public RegionBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
