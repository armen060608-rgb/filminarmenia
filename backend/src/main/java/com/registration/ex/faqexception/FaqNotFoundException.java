package com.registration.ex.faqexception;

import com.registration.ex.EntityNotFoundException;

public class FaqNotFoundException extends EntityNotFoundException {
    public FaqNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
