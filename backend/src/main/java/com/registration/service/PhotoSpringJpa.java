package com.registration.service;

import com.registration.entity.placefinder.NearbyPharmacy;
import com.registration.entity.placefinder.Photo;
import com.registration.entity.placefinder.Photo360;
import com.registration.ex.nearbypharmacyexception.NearbyPharmacyApiException;
import com.registration.ex.photo360exception.Photo360AlreadyExistsException;
import com.registration.ex.photo360exception.Photo360ApiException;
import com.registration.ex.photo360exception.Photo360BadRequestException;
import com.registration.ex.photo360exception.Photo360NotFoundException;
import com.registration.ex.photoexception.PhotoAlreadyExistsException;
import com.registration.ex.photoexception.PhotoApiException;
import com.registration.ex.photoexception.PhotoBadRequestException;
import com.registration.ex.photoexception.PhotoNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.PhotoRepository;
import com.registration.request.NearbyPharmaciesRequest;
import com.registration.request.Photo360Request;
import com.registration.request.PhotoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoSpringJpa implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public List<PhotoRequest> getPhotos() {
        try {
            return photoRepository.findAll()
                    .stream()
                    .map(Photo::toPhotoRequest)
                    .toList();
        } catch (Exception e) {
            throw new PhotoApiException(messageService.getMessage("photo.getting.all.error"), e);
        }
    }


    @Override
    public List<PhotoRequest> getPhotoss() {
        try {
            return photoRepository.findAll()
                    .stream()
                    .map(Photo::toPhotoRequestFull)
                    .toList();
        } catch (Exception e) {
            throw new PhotoApiException(messageService.getMessage("photo.getting.all.error"), e);
        }
    }

    @Override
    public PhotoRequest getPhotoById(UUID id) {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new PhotoNotFoundException(messageService.getMessage("photo.not.found")));
        return photo.toPhotoRequest();
    }


    @Override
    public PhotoRequest createPhoto(PhotoRequest photoRequest) {

        if (photoRequest.getId() != null) {
            throw new PhotoBadRequestException(messageService.getMessage("photo.id.must.null"));
        }

        validateDuplicate(null, photoRequest.getName());
        photoRequest.setName(photoRequest.getName());
        photoRequest.setSize(photoRequest.getSize());
        photoRequest.setPath(photoRequest.getPath());
        photoRequest.setNameRu(photoRequest.getNameRu());
        photoRequest.setNameHy(photoRequest.getNameHy());
        photoRequest.setDescription(photoRequest.getDescription());
        photoRequest.setDescriptionRu(photoRequest.getDescriptionRu());
        photoRequest.setDescriptionHy(photoRequest.getDescriptionHy());

        Photo photo = new Photo(photoRequest);

        try {
            photo = photoRepository.save(photo);
        } catch (Exception e) {
            throw new PhotoApiException(messageService.getMessage("photo.creating.error"), e);
        }

        PhotoRequest newPhoto = photo.toPhotoRequest();

        return newPhoto;
    }

    @Override
    public PhotoRequest updatePhoto(UUID id, PhotoRequest photoRequest) {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new PhotoNotFoundException(messageService.getMessage("photo.not.found")));

        photo.setName(photoRequest.getName());
        photo.setSize(photoRequest.getSize());
        photo.setPath(photoRequest.getPath());
        photo.setNameRu(photoRequest.getNameRu());
        photo.setNameHy(photoRequest.getNameHy());
        photo.setDescription(photoRequest.getDescription());
        photo.setDescriptionRu(photoRequest.getDescriptionRu());
        photo.setDescriptionHy(photoRequest.getDescriptionHy());


        try {
            Photo updated = photoRepository.save(photo);
            return updated.toPhotoRequest();
        } catch (Exception e) {
            throw new PhotoApiException(messageService.getMessage("photo.updating.error"), e);
        }
    }

    @Override
    public void deletePhoto(UUID id) {
        photoRepository.findById(id).orElseThrow(() ->
                new PhotoNotFoundException(messageService.getMessage("photo.not.found")));
        try {
            photoRepository.deleteById(id);
        } catch (Exception e) {
            throw new PhotoApiException(messageService.getMessage("photo.deleting.error"), e);
        }
    }

    private void validateDuplicate(UUID id, String name) {
        Optional<Photo> existing = photoRepository.findByName(name);

        if (existing.isPresent()) {
            Photo photo = existing.get();

            if (!photo.getId().equals(id)) {
                throw new PhotoAlreadyExistsException(
                        messageService.getMessage("photo.already.exists")
                );
            }
        }
    }
}