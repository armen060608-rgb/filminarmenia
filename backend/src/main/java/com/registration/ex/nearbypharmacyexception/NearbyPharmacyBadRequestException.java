package com.registration.ex.nearbypharmacyexception;

import com.registration.ex.BadRequestException;

public class NearbyPharmacyBadRequestException extends BadRequestException {
    public NearbyPharmacyBadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
