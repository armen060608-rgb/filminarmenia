package com.registration.ex.photoexception;

import com.registration.ex.BadRequestException;

public class PhotoBadRequestException extends BadRequestException {
    public PhotoBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
