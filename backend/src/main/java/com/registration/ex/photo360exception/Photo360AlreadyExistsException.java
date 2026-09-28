package com.registration.ex.photo360exception;

import com.registration.ex.ResourceAlreadyExistException;

public class Photo360AlreadyExistsException extends ResourceAlreadyExistException {
    public Photo360AlreadyExistsException(String message) {
        super(message);
    }
}
