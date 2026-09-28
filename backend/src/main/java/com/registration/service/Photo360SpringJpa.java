package com.registration.service;

import com.registration.entity.placefinder.NearbyPharmacy;
import com.registration.entity.placefinder.Photo;
import com.registration.entity.placefinder.Photo360;
import com.registration.ex.nearbypharmacyexception.NearbyPharmacyAlreadyExistsException;
import com.registration.ex.nearbypharmacyexception.NearbyPharmacyApiException;
import com.registration.ex.nearbypharmacyexception.NearbyPharmacyNotFoundException;
import com.registration.ex.photo360exception.Photo360AlreadyExistsException;
import com.registration.ex.photo360exception.Photo360ApiException;
import com.registration.ex.photo360exception.Photo360BadRequestException;
import com.registration.ex.photo360exception.Photo360NotFoundException;
import com.registration.ex.userexceptions.UserBadRequestException;
import com.registration.helper.MessageService;
import com.registration.repository.Photo360Repository;
import com.registration.request.NearbyPharmaciesRequest;
import com.registration.request.Photo360Request;
import com.registration.request.RegionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class Photo360SpringJpa implements Photo360Service {

    @Autowired
    private Photo360Repository photo360Repository;

    @Autowired
    private MessageService messageService;

    @Override
    public List<Photo360Request> getPhoto360() {
        try {
            return photo360Repository.findAll()
                    .stream()
                    .map(Photo360::toPhoto360Request)
                    .toList();
        } catch (Exception e) {
            throw new Photo360ApiException(messageService.getMessage("photo360.getting.all.error"), e);
        }
    }

    @Override
    public List<Photo360Request> getPhoto360s() {
        try {
            return photo360Repository.findAll()
                    .stream()
                    .map(Photo360::toPhoto360RequestFull)
                    .toList();
        } catch (Exception e) {
            throw new Photo360ApiException(messageService.getMessage("photo360.getting.all.error"), e);
        }
    }


    @Override
    public Photo360Request getPhoto360ById(UUID id) {
        Photo360 photo360 = photo360Repository.findById(id)
                .orElseThrow(() -> new Photo360NotFoundException(messageService.getMessage("photo360.not.found")));
        return photo360.toPhoto360Request();
    }


    @Override
    public Photo360Request createPhoto360(Photo360Request photo360Request) {

        if (photo360Request.getId() != null) {
            throw new Photo360BadRequestException(messageService.getMessage("photo360.id.must.null"));
        }

        validateDuplicate(null, photo360Request.getName());
        photo360Request.setName(photo360Request.getName());
        photo360Request.setSize(photo360Request.getSize());
        photo360Request.setPath(photo360Request.getPath());
        photo360Request.setDescription(photo360Request.getDescription());
        photo360Request.setNameRu(photo360Request.getNameRu());
        photo360Request.setNameHy(photo360Request.getNameHy());
        photo360Request.setDescriptionRu(photo360Request.getDescriptionRu());
        photo360Request.setDescriptionHy(photo360Request.getDescriptionHy());

        Photo360 photo360 = new Photo360(photo360Request);

        try {
            photo360 = photo360Repository.save(photo360);
        } catch (Exception e) {
            throw new Photo360ApiException(messageService.getMessage("photo360.creating.error"), e);
        }

        Photo360Request newPhoto360 = photo360.toPhoto360Request();

        return newPhoto360;
    }

    @Override
    public Photo360Request updatePhoto360(UUID id, Photo360Request photo360Request) {
        Photo360 photo360 = photo360Repository.findById(id)
                .orElseThrow(() -> new Photo360NotFoundException(messageService.getMessage("photo360.not.found")));

        photo360.setName(photo360Request.getName());
        photo360.setSize(photo360Request.getSize());
        photo360.setPath(photo360.getPath());
        photo360.setDescription(photo360Request.getDescription());
        photo360.setNameRu(photo360Request.getNameRu());
        photo360.setNameHy(photo360Request.getNameHy());
        photo360.setDescriptionRu(photo360Request.getDescriptionRu());
        photo360.setDescriptionHy(photo360Request.getDescriptionHy());

        try {
            Photo360 updated = photo360Repository.save(photo360);
            return updated.toPhoto360Request();
        } catch (Exception e) {
            throw new Photo360ApiException(messageService.getMessage("photo360.updating.error"), e);
        }
    }

    @Override
    public void deletePhoto360(UUID id) {
        photo360Repository.findById(id).orElseThrow(() ->
                new Photo360NotFoundException(messageService.getMessage("photo360.not.found")));
        try {
            photo360Repository.deleteById(id);
        } catch (Exception e) {
            throw new Photo360ApiException(messageService.getMessage("photo360.deleting.error"), e);
        }
    }

    private void validateDuplicate(UUID id, String name) {
        Optional<Photo360> existing = photo360Repository.findByName(name);

        if (existing.isPresent()) {
            Photo360 photo360 = existing.get();

            if (!photo360.getId().equals(id)) {
                throw new Photo360AlreadyExistsException(
                        messageService.getMessage("photo360.already.exists")
                );
            }
        }
    }
}