package com.registration.service;

import com.registration.entity.placefinder.PlaceType;
import com.registration.ex.placetypeexception.PlaceTypeAlreadyExistsException;
import com.registration.ex.placetypeexception.PlaceTypeApiException;
import com.registration.ex.placetypeexception.PlaceTypeNotFoundException;
import com.registration.ex.userexceptions.UserBadRequestException;
import com.registration.helper.MessageService;
import com.registration.repository.PlaceTypeRepository;
import com.registration.request.PlaceTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlaceTypeSpringJpa implements PlaceTypeService {

    @Autowired
    private PlaceTypeRepository placeTypeRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public List<PlaceTypeRequest> getAllPlaceTypes() {
        try {
            return placeTypeRepository.findAll()
                    .stream()
                    .map(PlaceType::toPlaceTypeRequest)
                    .toList();
        } catch (Exception e) {
            throw new PlaceTypeApiException(messageService.getMessage("placetype.getting.all.error"), e);
        }
    }


    @Override
    public List<PlaceTypeRequest> getAllPlaceTypess() {
        try {
            return placeTypeRepository.findAll()
                    .stream()
                    .map(PlaceType::toPlaceTypeRequestFull)
                    .toList();
        } catch (Exception e) {
            throw new PlaceTypeApiException(messageService.getMessage("placetype.getting.all.error"), e);
        }
    }

    @Override
    public PlaceTypeRequest getPlaceTypeById(UUID id) {
        PlaceType placeEntity = placeTypeRepository.findById(id)
                .orElseThrow(() -> new PlaceTypeNotFoundException(messageService.getMessage("placetype.not.found")));
        return placeEntity.toPlaceTypeRequest();
    }

    @Override
    public PlaceTypeRequest createPlaceType(PlaceTypeRequest placeTypeRequest) {

        if (placeTypeRequest.getId() != null) {
            throw new UserBadRequestException(messageService.getMessage("placetype.id.must.null"));
        }

        validateDuplicate(null, placeTypeRequest.getName());
        placeTypeRequest.setName(placeTypeRequest.getName());
        placeTypeRequest.setNameRu(placeTypeRequest.getNameRu());
        placeTypeRequest.setNameHy(placeTypeRequest.getNameHy());
        placeTypeRequest.setDescription(placeTypeRequest.getDescription());
        placeTypeRequest.setDescriptionRu(placeTypeRequest.getDescriptionRu());
        placeTypeRequest.setDescriptionHy(placeTypeRequest.getDescriptionHy());

        PlaceType placeType = new PlaceType(placeTypeRequest);

        try {
            placeType = placeTypeRepository.save(placeType);
        } catch (Exception e) {
            throw new PlaceTypeApiException(messageService.getMessage("placetype.creating.error"), e);
        }

        PlaceTypeRequest newPlaceType = placeType.toPlaceTypeRequest();

        return newPlaceType;
    }

    @Override
    public PlaceTypeRequest updatePlaceType(UUID id, PlaceTypeRequest placeTypeRequest) {
        PlaceType placeType = placeTypeRepository.findById(id)
                .orElseThrow(() -> new PlaceTypeNotFoundException(messageService.getMessage("placetype.not.found")));

        placeType.setName(placeTypeRequest.getName());
        placeType.setNameRu(placeTypeRequest.getNameRu());
        placeType.setNameHy(placeTypeRequest.getNameHy());
        placeType.setDescription(placeTypeRequest.getDescription());
        placeType.setDescriptionRu(placeTypeRequest.getDescriptionRu());
        placeType.setDescriptionHy(placeTypeRequest.getDescriptionHy());

        try {
            PlaceType updated = placeTypeRepository.save(placeType);
            return updated.toPlaceTypeRequest();
        } catch (Exception e) {
            throw new PlaceTypeApiException(messageService.getMessage("placetype.updating.error"), e);
        }
    }

    @Override
    public void deletePlaceType(UUID id) {
        placeTypeRepository.findById(id).orElseThrow(() ->
                new PlaceTypeNotFoundException(messageService.getMessage("placetype.not.found")));
        try {
            placeTypeRepository.deleteById(id);
        } catch (Exception e) {
            throw new PlaceTypeApiException(messageService.getMessage("placetype.deleting.error"), e);
        }
    }

    private void validateDuplicate(UUID id, String name) {
        Optional<PlaceType> existing = placeTypeRepository.findByName(name);

        if (existing.isPresent()) {
            PlaceType placeType = existing.get();

            if (!placeType.getId().equals(id)) {
                throw new PlaceTypeAlreadyExistsException(
                        messageService.getMessage("placetype.already.exists")
                );
            }
        }
    }
}