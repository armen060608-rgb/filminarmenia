package com.registration.service;

import com.registration.entity.placefinder.ClimateZone;
import com.registration.ex.climatezoneexception.ClimateZoneAlreadyExistException;
import com.registration.ex.climatezoneexception.ClimateZoneApiException;
import com.registration.ex.climatezoneexception.ClimateZoneBadRequestException;
import com.registration.ex.climatezoneexception.ClimateZoneNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.ClimateZoneRepository;
import com.registration.request.ClimateZoneRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClimateZoneSpringJpa implements ClimateZoneService {

    @Autowired
    private ClimateZoneRepository climateZoneRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public List<ClimateZoneRequest> getClimateZones() {
        try {
            return climateZoneRepository.findAll()
                    .stream()
                    .map(ClimateZone::toClimateZoneRequest)
                    .toList();
        } catch (Exception e) {
            throw new ClimateZoneApiException(messageService.getMessage("climatezone.getting.all.error"), e);
        }
    }

    @Override
    public ClimateZoneRequest getClimateZoneById(UUID id) {
        ClimateZone climateZone = climateZoneRepository.findById(id)
                .orElseThrow(() -> new ClimateZoneNotFoundException(messageService.getMessage("climatezone.not.found")));
        return climateZone.toClimateZoneRequest();
    }

    @Override
    public List<ClimateZoneRequest> getClimateZoness() {
        try {
            return climateZoneRepository.findAll()
                    .stream()
                    .map(ClimateZone::toClimateZoneRequestFull)
                    .toList();
        }catch (Exception e) {
            throw new ClimateZoneApiException(messageService.getMessage("climatezone.getting.all.error"), e);
        }

    }


    @Override
    public ClimateZoneRequest createClimateZone(ClimateZoneRequest climateZoneRequest) {

        if (climateZoneRequest.getId() != null) {
            throw new ClimateZoneBadRequestException(messageService.getMessage("climatezone.id.must.null"));
        }

        validateDuplicate(null, climateZoneRequest.getName());
        climateZoneRequest.setName(climateZoneRequest.getName());
        climateZoneRequest.setNameRu(climateZoneRequest.getNameRu());
        climateZoneRequest.setNameHy(climateZoneRequest.getNameHy());

        ClimateZone climateZone = new ClimateZone(climateZoneRequest);

        try {
            climateZone = climateZoneRepository.save(climateZone);
        } catch (Exception e) {
            throw new ClimateZoneApiException(messageService.getMessage("climatezone.creating.error"), e);
        }

        ClimateZoneRequest newClimateZone = climateZone.toClimateZoneRequest();

        return newClimateZone;
    }

    @Override
    public ClimateZoneRequest updateClimateZone(UUID id, ClimateZoneRequest climateZoneRequest) {
        ClimateZone climateZone = climateZoneRepository.findById(id)
                .orElseThrow(() -> new ClimateZoneNotFoundException(messageService.getMessage("climatezone.not.found")));

        climateZone.setName(climateZoneRequest.getName());
        climateZone.setNameRu(climateZoneRequest.getNameRu());
        climateZone.setNameHy(climateZoneRequest.getNameHy());

        try {
            ClimateZone updated = climateZoneRepository.save(climateZone);
            return updated.toClimateZoneRequest();
        } catch (Exception e) {
            throw new ClimateZoneApiException(messageService.getMessage("climatezone.updating.error"), e);
        }
    }

    @Override
    public void deleteClimateZone(UUID id) {
        climateZoneRepository.findById(id).orElseThrow(() ->
                new ClimateZoneNotFoundException(messageService.getMessage("climatezone.not.found")));
        try {
            climateZoneRepository.deleteById(id);
        } catch (Exception e) {
            throw new ClimateZoneApiException(messageService.getMessage("climatezone.deleting.error"), e);
        }
    }

    private void validateDuplicate(UUID id, String name) {
        Optional<ClimateZone> existing = climateZoneRepository.findByName(name);

        if (existing.isPresent()) {
            ClimateZone climateZone = existing.get();

            if (!climateZone.getId().equals(id)) {
                throw new ClimateZoneAlreadyExistException(
                        messageService.getMessage("climatezone.already.exists")
                );
            }
        }
    }
}