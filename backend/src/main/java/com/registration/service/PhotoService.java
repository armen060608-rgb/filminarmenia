package com.registration.service;

import com.registration.request.Photo360Request;
import com.registration.request.PhotoRequest;

import java.util.List;
import java.util.UUID;

public interface PhotoService {

    List<PhotoRequest> getPhotos();
    List<PhotoRequest> getPhotoss();
    PhotoRequest getPhotoById(UUID id);
    PhotoRequest createPhoto(PhotoRequest photoRequest);
    PhotoRequest updatePhoto(UUID id, PhotoRequest photoRequest);
    void deletePhoto(UUID id);
}
