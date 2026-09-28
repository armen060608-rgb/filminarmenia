package com.registration.ex.photo360exception;

import com.registration.ex.BadRequestException;

public class Photo360BadRequestException extends BadRequestException {
    public Photo360BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
