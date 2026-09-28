package com.registration.service;

import com.registration.entity.placefinder.NearbyHospitals;
import com.registration.entity.placefinder.NearbyHotels;
import com.registration.ex.gasstationexception.GasStationApiException;
import com.registration.ex.nearbyhospitalsexception.NearbyHospitalsAlreadyExistsException;
import com.registration.ex.nearbyhospitalsexception.NearbyHospitalsApiException;
import com.registration.ex.nearbyhospitalsexception.NearbyHospitalsBadRequestException;
import com.registration.ex.nearbyhospitalsexception.NearbyHospitalsNotFoundException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsBadRequestException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.NearbyHospitalsRepository;
import com.registration.request.NearbyHospitalsRequest;
import com.registration.request.NearbyHotelsRequest;
import com.registration.request.PhotoRequest;
import com.registration.request.RegionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class NearbyHospitalsSpringJpa implements NearbyHospitalsService{

    @Autowired
    private NearbyHospitalsRepository nearbyHospitalsRepository;

    @Autowired
    private RegionService regionService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private MessageService messageService;


    @Override
    public List<NearbyHospitalsRequest> getNearbyHospitals() {
        try {
            return nearbyHospitalsRepository.findAll()
                    .stream()
                    .map(NearbyHospitals::toNearbyHospitalsRequestFull)
                    .toList();
        } catch (Exception e) {
            throw new NearbyHospitalsApiException("Problem during getting all nearby hospitals", e);
        }
    }


    @Override
    public List<NearbyHospitalsRequest> getNearbyHospitalss() {
        try {
            return nearbyHospitalsRepository.findAll()
                    .stream()
                    .map(NearbyHospitals::toNearbyHospitalsRequest)
                    .toList();
        } catch (Exception e) {
            throw new NearbyHospitalsApiException("Problem during getting all nearby hospitals", e);
        }
    }


    @Override
    public NearbyHospitalsRequest getNearbyHospitalById(UUID id) {
        NearbyHospitals nearbyHospitals = nearbyHospitalsRepository.findById(id)
                .orElseThrow(() -> new NearbyHospitalsNotFoundException("Nearby hospital not found with given ID"));
        return nearbyHospitals.toNearbyHospitalsRequest();
    }

    @Override
    public List<NearbyHospitalsRequest> getNearbyHospitalsByRegion(UUID regionId) {
        log.info("Fetching nearby hospitals for regionId: {}", regionId);

        if (regionId == null) {
            throw new NearbyHospitalsBadRequestException("RegionId cannot be null");
        }

        List<NearbyHospitals> nearbyHospitals = nearbyHospitalsRepository.findByRegionId(regionId);

        if (nearbyHospitals.isEmpty()) {
            throw new NearbyHospitalsNotFoundException(messageService.getMessage("nearbyhospital.not.found"));
        }

        return nearbyHospitals.stream()
                .map(NearbyHospitals::toNearbyHospitalsRequest)
                .toList();
    }


    @Override
    public NearbyHospitalsRequest createNearbyHospital(NearbyHospitalsRequest nearbyHospitalsRequest) {
        if (nearbyHospitalsRequest.getId() != null) {
            throw new NearbyHospitalsBadRequestException("User ID must be null");
        }

        validateDuplicate(null, nearbyHospitalsRequest.getName());
        nearbyHospitalsRequest.setName(nearbyHospitalsRequest.getName());
        nearbyHospitalsRequest.setDescription(nearbyHospitalsRequest.getDescription());
        nearbyHospitalsRequest.setDescriptionHy(nearbyHospitalsRequest.getDescriptionHy());
        nearbyHospitalsRequest.setDescriptionRu(nearbyHospitalsRequest.getDescriptionRu());
        nearbyHospitalsRequest.setNameHy(nearbyHospitalsRequest.getNameHy());
        nearbyHospitalsRequest.setNameRu(nearbyHospitalsRequest.getNameRu());
        try{
            RegionRequest region = regionService.getRegionById(nearbyHospitalsRequest.getRegionId());
            nearbyHospitalsRequest.setRegionId(region.getId());
        }catch (Exception e){
            throw new NearbyHospitalsApiException("Region not found");
        }
        try{
            PhotoRequest photo = photoService.getPhotoById(nearbyHospitalsRequest.getPhotoId());
            nearbyHospitalsRequest.setPhotoId(photo.getId());
        }catch (Exception e){
            throw new GasStationApiException("Photo not found");
        }
        NearbyHospitals nearbyHospitals = new NearbyHospitals(nearbyHospitalsRequest);

        try {
            nearbyHospitals = nearbyHospitalsRepository.save(nearbyHospitals);
        } catch (Exception e) {
            throw new NearbyHospitalsApiException("Problem during creating nearby hospital", e);
        }

        NearbyHospitalsRequest newNearbyHospital= nearbyHospitals.toNearbyHospitalsRequest();

        return newNearbyHospital;
    }
    @Override
    public NearbyHospitalsRequest updateNearbyHospital(UUID id, NearbyHospitalsRequest nearbyHospitalsRequest) {
        NearbyHospitals nearbyHospitals = nearbyHospitalsRepository.findById(id)
                .orElseThrow(() -> new NearbyHospitalsNotFoundException("Nearby hospital not found with given ID"));

        nearbyHospitals.setName(nearbyHospitalsRequest.getName());
        nearbyHospitals.setDescription(nearbyHospitalsRequest.getDescription());
        nearbyHospitals.setDescriptionHy(nearbyHospitalsRequest.getDescriptionHy());
        nearbyHospitals.setDescriptionRu(nearbyHospitalsRequest.getDescriptionRu());
        nearbyHospitals.setNameHy(nearbyHospitalsRequest.getNameHy());
        nearbyHospitals.setNameRu(nearbyHospitalsRequest.getNameRu());
        try{
            RegionRequest region = regionService.getRegionById(nearbyHospitalsRequest.getRegionId());
            nearbyHospitals.setRegionId(region.getId());
        }catch (Exception e){
            throw new NearbyHospitalsApiException("Region not found");
        }
        try{
            PhotoRequest photo = photoService.getPhotoById(nearbyHospitalsRequest.getPhotoId());
            nearbyHospitals.setPhotoId(photo.getId());
        }catch (Exception e){
            throw new GasStationApiException("Photo not found");
        }

        try {
            NearbyHospitals updated = nearbyHospitalsRepository.save(nearbyHospitals);
            return updated.toNearbyHospitalsRequest();
        } catch (Exception e) {
            throw new NearbyHospitalsApiException("Problem during updating nearby hospital", e);
        }
    }

    @Override
    public void deleteNearbyHospital(UUID id) {
        nearbyHospitalsRepository.findById(id).orElseThrow(() ->
                new NearbyHospitalsNotFoundException("Nearby hospital not found with given ID"));
        try {
            nearbyHospitalsRepository.deleteById(id);
        } catch (Exception e) {
            throw new NearbyHospitalsApiException("Problem during deleting nearby hospital", e);
        }

    }

    private void validateDuplicate(UUID id, String name) {
        Optional<NearbyHospitals> existing = nearbyHospitalsRepository.findByName(name);

        if (existing.isPresent()) {
            NearbyHospitals nearbyHospitals = existing.get();

            if (!nearbyHospitals.getId().equals(id)) {
                throw new NearbyHospitalsAlreadyExistsException(
                        "Nearby hospital with name '" + name + "' already exists"
                );
            }
        }
    }
}
