package com.registration.service;

import com.registration.entity.placefinder.NearbyHotels;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsAlreadyExistsException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsApiException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsBadRequestException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.NearbyHotelsRepository;
import com.registration.request.NearbyHotelsRequest;
import com.registration.request.RegionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class NearbyHotelsSpringJpa implements NearbyHotelsService {

    @Autowired
    private NearbyHotelsRepository nearbyHotelsRepository;

    @Autowired
    private RegionService regionService;

    @Autowired
    private MessageService messageService;

    @Override
    public List<NearbyHotelsRequest> getNearbyHotels() {
        try {
            return nearbyHotelsRepository.findAll()
                    .stream()
                    .map(NearbyHotels::toNearbyHotelsRequest)
                    .toList();
        } catch (Exception e) {
            throw new NearbyHotelsApiException(messageService.getMessage("nearbyhotel.getting.all.error"), e);
        }
    }


    @Override
    public List<NearbyHotelsRequest> getNearbyHotelss() {
        try {
            return nearbyHotelsRepository.findAll()
                    .stream()
                    .map(NearbyHotels::toNearbyHotelsRequestFull)
                    .toList();
        } catch (Exception e) {
            throw new NearbyHotelsApiException(messageService.getMessage("nearbyhotel.getting.all.error"), e);
        }
    }

    @Override
    public NearbyHotelsRequest getNearbyHotelById(UUID id) {
        NearbyHotels nearbyHotels = nearbyHotelsRepository.findById(id)
                .orElseThrow(() -> new NearbyHotelsNotFoundException(messageService.getMessage("nearbyhotel.not.found")));
        return nearbyHotels.toNearbyHotelsRequest();
    }

    @Override
    public List<NearbyHotelsRequest> getNearbyHotelsByRegion(UUID regionId) {
        log.info("Fetching nearby hotels for regionId: {}", regionId);

        if (regionId == null) {
            throw new NearbyHotelsBadRequestException("RegionId cannot be null");
        }

        List<NearbyHotels> nearbyHotels = nearbyHotelsRepository.findByRegionId(regionId);

        if (nearbyHotels.isEmpty()) {
            throw new NearbyHotelsNotFoundException(messageService.getMessage("nearbyhotel.not.found"));
        }

        return nearbyHotels.stream()
                .map(NearbyHotels::toNearbyHotelsRequest)
                .toList();
    }

    @Override
    public NearbyHotelsRequest createNearbyHotel(NearbyHotelsRequest nearbyHotelsRequest) {
        log.info("Creating nearby hotel with request: {}", nearbyHotelsRequest);

        if (nearbyHotelsRequest.getId() != null) {
            throw new NearbyHotelsBadRequestException(messageService.getMessage("nearbyhotel.id.must.null"));
        }

        if (nearbyHotelsRequest.getName() == null || nearbyHotelsRequest.getName().trim().isEmpty()) {
            throw new NearbyHotelsBadRequestException("Name cannot be empty");
        }
        if (nearbyHotelsRequest.getNameHy() == null || nearbyHotelsRequest.getNameHy().trim().isEmpty()) {
            throw new NearbyHotelsBadRequestException("NameHy cannot be empty");
        }
        if (nearbyHotelsRequest.getNameRu() == null || nearbyHotelsRequest.getNameRu().trim().isEmpty()) {
            throw new NearbyHotelsBadRequestException("NameRu cannot be empty");
        }
        if (nearbyHotelsRequest.getDescription() == null || nearbyHotelsRequest.getDescription().trim().isEmpty()) {
            throw new NearbyHotelsBadRequestException("Description cannot be empty");
        }
        if (nearbyHotelsRequest.getDescriptionHy() == null || nearbyHotelsRequest.getDescriptionHy().trim().isEmpty()) {
            throw new NearbyHotelsBadRequestException("DescriptionHy cannot be empty");
        }
        if (nearbyHotelsRequest.getDescriptionRu() == null || nearbyHotelsRequest.getDescriptionRu().trim().isEmpty()) {
            throw new NearbyHotelsBadRequestException("DescriptionRu cannot be empty");
        }
        if (nearbyHotelsRequest.getPhotoId() == null) {
            throw new NearbyHotelsBadRequestException("PhotoId cannot be null");
        }
        if (nearbyHotelsRequest.getRegionId() == null) {
            throw new NearbyHotelsBadRequestException("RegionId cannot be null");
        }

        Optional<NearbyHotels> existing = nearbyHotelsRepository.findByName(nearbyHotelsRequest.getName());
        if (existing.isPresent()) {
            throw new NearbyHotelsAlreadyExistsException(
                    messageService.getMessage("nearbyhotel.already.exists")
            );
        }

        RegionRequest region;
        try {
            region = regionService.getRegionById(nearbyHotelsRequest.getRegionId());
            log.info("Found region with ID: {}", region.getId());
        } catch (Exception e) {
            log.error("Region not found with ID: {}", nearbyHotelsRequest.getRegionId());
            throw new NearbyHotelsBadRequestException(messageService.getMessage("validation.invalid.region"));
        }

        NearbyHotels nearbyHotels = NearbyHotels.builder()
                .name(nearbyHotelsRequest.getName())
                .nameHy(nearbyHotelsRequest.getNameHy())
                .nameRu(nearbyHotelsRequest.getNameRu())
                .rate(nearbyHotelsRequest.getRate())
                .website(nearbyHotelsRequest.getWebsite())
                .description(nearbyHotelsRequest.getDescription())
                .descriptionHy(nearbyHotelsRequest.getDescriptionHy())
                .descriptionRu(nearbyHotelsRequest.getDescriptionRu())
                .photoId(nearbyHotelsRequest.getPhotoId())
                .regionId(region.getId())
                .build();

        try {
            NearbyHotels savedHotel = nearbyHotelsRepository.save(nearbyHotels);
            log.info("Successfully saved nearby hotel with ID: {}", savedHotel.getId());
            return savedHotel.toNearbyHotelsRequest();
        } catch (Exception e) {
            log.error("Error saving nearby hotel: {}", e.getMessage(), e);
            throw new NearbyHotelsApiException(messageService.getMessage("nearbyhotel.creating.error"), e);
        }
    }

    @Override
    public NearbyHotelsRequest updateNearbyHotel(UUID id, NearbyHotelsRequest nearbyHotelsRequest) {
        log.info("Updating nearby hotel with ID: {} and request: {}", id, nearbyHotelsRequest);

        NearbyHotels nearbyHotels = nearbyHotelsRepository.findById(id)
                .orElseThrow(() -> new NearbyHotelsNotFoundException(messageService.getMessage("nearbyhotel.not.found")));

        log.info("Found existing nearby hotel: ID={}, name={}", nearbyHotels.getId(), nearbyHotels.getName());

        validateDuplicate(id, nearbyHotelsRequest.getName());

        try {
            RegionRequest region = regionService.getRegionById(nearbyHotelsRequest.getRegionId());
            nearbyHotels.setRegionId(region.getId());
            log.info("Region set successfully");
        } catch (Exception e) {
            log.error("Error setting region: {}", e.getMessage(), e);
            throw new NearbyHotelsBadRequestException(messageService.getMessage("validation.invalid.region"));
        }

        try {
            nearbyHotels.setName(nearbyHotelsRequest.getName());
            nearbyHotels.setNameHy(nearbyHotelsRequest.getNameHy());
            nearbyHotels.setNameRu(nearbyHotelsRequest.getNameRu());
            nearbyHotels.setRate(nearbyHotelsRequest.getRate());
            nearbyHotels.setWebsite(nearbyHotelsRequest.getWebsite());
            nearbyHotels.setDescription(nearbyHotelsRequest.getDescription());
            nearbyHotels.setDescriptionHy(nearbyHotelsRequest.getDescriptionHy());
            nearbyHotels.setDescriptionRu(nearbyHotelsRequest.getDescriptionRu());
            nearbyHotels.setPhotoId(nearbyHotelsRequest.getPhotoId());
        } catch (Exception e) {
            log.error("Error setting fields: {}", e.getMessage(), e);
            throw new NearbyHotelsBadRequestException("Error setting fields: " + e.getMessage());
        }

        log.info("All fields set successfully");

        try {
            NearbyHotels updated = nearbyHotelsRepository.save(nearbyHotels);
            log.info("Successfully updated nearby hotel with ID: {}", updated.getId());
            return updated.toNearbyHotelsRequest();
        } catch (Exception e) {
            log.error("Error updating nearby hotel: {}", e.getMessage(), e);
            throw new NearbyHotelsApiException(messageService.getMessage("nearbyhotel.updating.error"), e);
        }
    }

    @Override
    public void deleteNearbyHotel(UUID id) {
        nearbyHotelsRepository.findById(id).orElseThrow(() ->
                new NearbyHotelsNotFoundException(messageService.getMessage("nearbyhotel.not.found")));
        try {
            nearbyHotelsRepository.deleteById(id);
        } catch (Exception e) {
            throw new NearbyHotelsApiException(messageService.getMessage("nearbyhotel.deleting.error"), e);
        }
    }

    private void validateDuplicate(UUID id, String name) {
        Optional<NearbyHotels> existing = nearbyHotelsRepository.findByName(name);

        if (existing.isPresent()) {
            NearbyHotels nearbyHotels = existing.get();

            if (!nearbyHotels.getId().equals(id)) {
                throw new NearbyHotelsAlreadyExistsException(
                        messageService.getMessage("nearbyhotel.already.exists")
                );
            }
        }
    }
}