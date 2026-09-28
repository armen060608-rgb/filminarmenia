package com.registration.service;

import com.registration.request.NearbyPharmaciesRequest;
import com.registration.request.Photo360Request;

import java.util.List;
import java.util.UUID;

public interface Photo360Service {

    List<Photo360Request> getPhoto360();
    List<Photo360Request> getPhoto360s();
    Photo360Request getPhoto360ById(UUID id);
    Photo360Request createPhoto360(Photo360Request photo360Request);
    Photo360Request updatePhoto360(UUID id, Photo360Request photo360Request);
    void deletePhoto360(UUID id);
}
