package com.registration.ex.placetypeexception;

import com.registration.ex.BadRequestException;

public class PlaceTypeBadRequestException extends BadRequestException {
    public PlaceTypeBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
