package com.registration.service;

import com.registration.entity.placefinder.ClimateZone;
import com.registration.entity.placefinder.NearbyHotels;
import com.registration.entity.placefinder.NearbyPharmacy;
import com.registration.entity.placefinder.TransportType;
import com.registration.ex.climatezoneexception.ClimateZoneApiException;
import com.registration.ex.climatezoneexception.ClimateZoneNotFoundException;
import com.registration.ex.gasstationexception.GasStationApiException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsBadRequestException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsNotFoundException;
import com.registration.ex.nearbypharmacyexception.NearbyPharmacyAlreadyExistsException;
import com.registration.ex.nearbypharmacyexception.NearbyPharmacyApiException;
import com.registration.ex.nearbypharmacyexception.NearbyPharmacyBadRequestException;
import com.registration.ex.nearbypharmacyexception.NearbyPharmacyNotFoundException;
import com.registration.ex.transporttypeexcpetion.TransportTypeAlreadyExistsException;
import com.registration.ex.transporttypeexcpetion.TransportTypeApiException;
import com.registration.ex.transporttypeexcpetion.TransportTypeNotFoundException;
import com.registration.ex.userexceptions.UserApiException;
import com.registration.ex.userexceptions.UserBadRequestException;
import com.registration.helper.MessageService;
import com.registration.repository.ClimateZoneRepository;
import com.registration.repository.NearbyPharmaciesRepository;
import com.registration.request.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class NearbyPharmaciesSpringJpa implements NearbyPharmaciesService{

    @Autowired
    private NearbyPharmaciesRepository nearbyPharmaciesRepository;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private MessageService messageService;


    @Override
    public List<NearbyPharmaciesRequest> getNearbyPharmacies() {
        try {
            return nearbyPharmaciesRepository.findAll()
                    .stream()
                    .map(NearbyPharmacy::toNearbyPharmaciesRequest)
                    .toList();
        } catch (Exception e) {
            throw new NearbyPharmacyApiException("Problem during getting all nearby pharmacies", e);
        }
    }


    @Override
    public List<NearbyPharmaciesRequest> getNearbyPharmaciess() {
        try {
            return nearbyPharmaciesRepository.findAll()
                    .stream()
                    .map(NearbyPharmacy::toNearbyPharmaciesRequestFull)
                    .toList();
        } catch (Exception e) {
            throw new NearbyPharmacyApiException("Problem during getting all nearby pharmacies", e);
        }
    }


    @Override
    public NearbyPharmaciesRequest getNearbyPharmacyById(UUID id) {
        NearbyPharmacy nearbyPharmacy = nearbyPharmaciesRepository.findById(id)
                .orElseThrow(() -> new NearbyPharmacyNotFoundException("Nearby pharmacy not found with given ID"));
        return nearbyPharmacy.toNearbyPharmaciesRequest();
    }


    @Override
    public List<NearbyPharmaciesRequest> getNearbyPharmacyByRegion(UUID regionId) {
        log.info("Fetching nearby pharmacy for regionId: {}", regionId);

        if (regionId == null) {
            throw new NearbyPharmacyBadRequestException("RegionId cannot be null");
        }

        List<NearbyPharmacy> nearbyPharmacy = nearbyPharmaciesRepository.findByRegionId(regionId);

        if (nearbyPharmacy.isEmpty()) {
            throw new NearbyHotelsNotFoundException(messageService.getMessage("nearbyhotel.not.found"));
        }

        return nearbyPharmacy.stream()
                .map(NearbyPharmacy::toNearbyPharmaciesRequest)
                .toList();
    }


    @Override
    public NearbyPharmaciesRequest createNearbyPharmacy(NearbyPharmaciesRequest nearbyPharmaciesRequest) {


        if (nearbyPharmaciesRequest.getId() != null) {
            throw new UserBadRequestException("User ID must be null");
        }

        validateDuplicate(null, nearbyPharmaciesRequest.getName());
        nearbyPharmaciesRequest.setName(nearbyPharmaciesRequest.getName());
        nearbyPharmaciesRequest.setDescription(nearbyPharmaciesRequest.getDescription());
        nearbyPharmaciesRequest.setDescriptionHy(nearbyPharmaciesRequest.getDescriptionHy());
        nearbyPharmaciesRequest.setDescriptionRu(nearbyPharmaciesRequest.getDescriptionRu());
        nearbyPharmaciesRequest.setNameHy(nearbyPharmaciesRequest.getNameHy());
        nearbyPharmaciesRequest.setNameRu(nearbyPharmaciesRequest.getNameRu());
        try{
            RegionRequest region = regionService.getRegionById(nearbyPharmaciesRequest.getRegionId());
            nearbyPharmaciesRequest.setRegionId(region.getId());
        } catch (Exception e) {
            throw new NearbyPharmacyApiException("Region not found");
        }
        try{
            PhotoRequest photo = photoService.getPhotoById(nearbyPharmaciesRequest.getPhotoId());
            nearbyPharmaciesRequest.setPhotoId(photo.getId());
        }catch (Exception e){
            throw new GasStationApiException("Photo not found");
        }

        NearbyPharmacy nearbyPharmacy = new NearbyPharmacy(nearbyPharmaciesRequest);

        try {
            nearbyPharmacy = nearbyPharmaciesRepository.save(nearbyPharmacy);
        } catch (Exception e) {
            throw new UserApiException("Problem during creating nearby pharmacy", e);
        }

        NearbyPharmaciesRequest newNearbyPharmacy = nearbyPharmacy.toNearbyPharmaciesRequest();


        return newNearbyPharmacy;
    }

    @Override
    public NearbyPharmaciesRequest updateNearbyPharmacy(UUID id, NearbyPharmaciesRequest nearbyPharmaciesRequest) {
        NearbyPharmacy nearbyPharmacy = nearbyPharmaciesRepository.findById(id)
                .orElseThrow(() -> new NearbyPharmacyNotFoundException("Nearby pharmacy not found with given ID"));

        nearbyPharmacy.setName(nearbyPharmaciesRequest.getName());
        nearbyPharmacy.setDescription(nearbyPharmaciesRequest.getDescription());
        nearbyPharmacy.setDescriptionHy(nearbyPharmaciesRequest.getDescriptionHy());
        nearbyPharmacy.setDescriptionRu(nearbyPharmaciesRequest.getDescriptionRu());
        nearbyPharmacy.setNameHy(nearbyPharmaciesRequest.getNameHy());
        nearbyPharmacy.setNameRu(nearbyPharmaciesRequest.getNameRu());
        try{
            RegionRequest region = regionService.getRegionById(nearbyPharmaciesRequest.getRegionId());
            nearbyPharmacy.setRegionId(region.getId());
        } catch (Exception e) {
            throw new NearbyPharmacyApiException("Region not found");
        }
        try{
            PhotoRequest photo = photoService.getPhotoById(nearbyPharmaciesRequest.getPhotoId());
            nearbyPharmacy.setPhotoId(photo.getId());
        }catch (Exception e){
            throw new GasStationApiException("Photo not found");
        }

        try {
            NearbyPharmacy updated = nearbyPharmaciesRepository.save(nearbyPharmacy);
            return updated.toNearbyPharmaciesRequest();
        } catch (Exception e) {
            throw new NearbyPharmacyApiException("Problem during updating nearby pharmacy", e);
        }

    }

    @Override
    public void deleteNearbyPharmacy(UUID id) {
        nearbyPharmaciesRepository.findById(id).orElseThrow(() ->
                new NearbyPharmacyNotFoundException("Nearby pharmacy not found with given ID"));
        try {
            nearbyPharmaciesRepository.deleteById(id);
        } catch (Exception e) {
            throw new NearbyPharmacyApiException("Problem during deleting nearby pharmacy", e);
        }

    }
    private void validateDuplicate(UUID id, String name) {
        Optional<NearbyPharmacy> existing = nearbyPharmaciesRepository.findByName(name);

        if (existing.isPresent()) {
            NearbyPharmacy nearbyPharmacy = existing.get();

            if (!nearbyPharmacy.getId().equals(id)) {
                throw new NearbyPharmacyAlreadyExistsException(
                        "Nearby pharmacy with name '" + name + "' already exists"
                );
            }
        }
    }
}
