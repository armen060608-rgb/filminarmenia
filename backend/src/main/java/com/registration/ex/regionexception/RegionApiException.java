package com.registration.ex.regionexception;

import com.registration.ex.ApiException;

public class RegionApiException extends ApiException {
    public RegionApiException(String errorMessage) {
        super(errorMessage);
    }

    public RegionApiException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
