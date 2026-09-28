package com.registration.service;

import com.registration.entity.placefinder.RoadQuality;
import com.registration.ex.roadqualityexception.RoadQualityAlreadyExistsException;
import com.registration.ex.roadqualityexception.RoadQualityApiException;
import com.registration.ex.roadqualityexception.RoadQualityNotFoundException;
import com.registration.ex.userexceptions.UserBadRequestException;
import com.registration.helper.MessageService;
import com.registration.repository.RoadQualityRepository;
import com.registration.request.RoadQualityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoadQualitySpringJpa implements RoadQualityService {

    @Autowired
    private RoadQualityRepository roadQualityRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public List<RoadQualityRequest> getAllRoadQualities() {
        try {
            return roadQualityRepository.findAll()
                    .stream()
                    .map(RoadQuality::toRoadQualityRequest)
                    .toList();
        } catch (Exception e) {
            throw new RoadQualityApiException(messageService.getMessage("roadquality.getting.all.error"), e);
        }
    }

    @Override
    public RoadQualityRequest getRoadQualityById(UUID id) {
        RoadQuality roadQuality = roadQualityRepository.findById(id)
                .orElseThrow(() -> new RoadQualityNotFoundException(messageService.getMessage("roadquality.not.found")));
        return roadQuality.toRoadQualityRequest();
    }

    @Override
    public RoadQualityRequest createRoadQuality(RoadQualityRequest roadQualityRequest) {

        if (roadQualityRequest.getId() != null) {
            throw new UserBadRequestException(messageService.getMessage("roadquality.id.must.null"));
        }

        validateDuplicate(null, roadQualityRequest.getName());
        roadQualityRequest.setName(roadQualityRequest.getName());
        roadQualityRequest.setNameRu(roadQualityRequest.getNameRu());
        roadQualityRequest.setNameHy(roadQualityRequest.getNameHy());
        roadQualityRequest.setDescription(roadQualityRequest.getDescription());
        roadQualityRequest.setDescriptionRu(roadQualityRequest.getDescriptionRu());
        roadQualityRequest.setDescriptionHy(roadQualityRequest.getDescriptionHy());

        RoadQuality roadQuality = new RoadQuality(roadQualityRequest);

        try {
            roadQuality = roadQualityRepository.save(roadQuality);
        } catch (Exception e) {
            throw new RoadQualityApiException(messageService.getMessage("roadquality.creating.error"), e);
        }

        RoadQualityRequest newRoadQuality = roadQuality.toRoadQualityRequest();

        return newRoadQuality;
    }

    @Override
    public RoadQualityRequest updateRoadQuality(UUID id, RoadQualityRequest roadQualityRequest) {
        RoadQuality roadQuality = roadQualityRepository.findById(id)
                .orElseThrow(() -> new RoadQualityNotFoundException(messageService.getMessage("roadquality.not.found")));

        roadQuality.setName(roadQualityRequest.getName());
        roadQuality.setNameRu(roadQualityRequest.getNameRu());
        roadQuality.setNameHy(roadQualityRequest.getNameHy());
        roadQuality.setDescription(roadQualityRequest.getDescription());
        roadQuality.setDescriptionRu(roadQualityRequest.getDescriptionRu());
        roadQuality.setDescriptionHy(roadQualityRequest.getDescriptionHy());

        try {
            RoadQuality updated = roadQualityRepository.save(roadQuality);
            return updated.toRoadQualityRequest();
        } catch (Exception e) {
            throw new RoadQualityApiException(messageService.getMessage("roadquality.updating.error"), e);
        }
    }

    @Override
    public void deleteRoadQuality(UUID id) {
        roadQualityRepository.findById(id).orElseThrow(() ->
                new RoadQualityNotFoundException(messageService.getMessage("roadquality.not.found")));
        try {
            roadQualityRepository.deleteById(id);
        } catch (Exception e) {
            throw new RoadQualityApiException(messageService.getMessage("roadquality.deleting.error"), e);
        }
    }

    private void validateDuplicate(UUID id, String name) {
        Optional<RoadQuality> existing = roadQualityRepository.findByName(name);

        if (existing.isPresent()) {
            RoadQuality roadQuality = existing.get();

            if (!roadQuality.getId().equals(id)) {
                throw new RoadQualityAlreadyExistsException(
                        messageService.getMessage("roadquality.already.exists")
                );
            }
        }
    }
}