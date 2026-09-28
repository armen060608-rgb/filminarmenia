package com.registration.ex.photoexception;

import com.registration.ex.ResourceAlreadyExistException;

public class PhotoAlreadyExistsException extends ResourceAlreadyExistException {
    public PhotoAlreadyExistsException(String message) {
        super(message);
    }
}
