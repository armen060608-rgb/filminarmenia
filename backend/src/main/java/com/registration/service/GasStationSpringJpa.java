package com.registration.service;

import com.registration.entity.placefinder.GasStation;
import com.registration.entity.placefinder.NearbyHotels;
import com.registration.ex.gasstationexception.GasStationAlreadyExistsException;
import com.registration.ex.gasstationexception.GasStationApiException;
import com.registration.ex.gasstationexception.GasStationBadRequestException;
import com.registration.ex.gasstationexception.GasStationNotFoundException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsBadRequestException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.GasStationsRepository;
import com.registration.request.GasStationsRequest;
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
public class GasStationSpringJpa implements GasStationService {

    @Autowired
    private GasStationsRepository gasStationsRepository;

    @Autowired
    private RegionService regionService;
    @Autowired
    private PhotoService photoService;


    @Autowired
    private MessageService messageService;

    @Override
    public List<GasStationsRequest> getGasStations() {
        try {
            return gasStationsRepository.findAll()
                    .stream()
                    .map(GasStation::toGasStationRequest)
                    .toList();
        } catch (Exception e) {
            throw new GasStationApiException(messageService.getMessage("gasstation.getting.all.error"), e);
        }
    }


    @Override
    public List<GasStationsRequest> getGasStationss() {
        try {
            return gasStationsRepository.findAll()
                    .stream()
                    .map(GasStation::toGasStationRequestFull)
                    .toList();
        } catch (Exception e) {
            throw new GasStationApiException(messageService.getMessage("gasstation.getting.all.error"), e);
        }
    }


    @Override
    public GasStationsRequest getGasStationById(UUID id) {
        GasStation gasStation = gasStationsRepository.findById(id)
                .orElseThrow(() -> new GasStationNotFoundException(messageService.getMessage("gasstation.not.found")));
        return gasStation.toGasStationRequest();
    }


    @Override
    public List<GasStationsRequest> getGasStationByRegion(UUID regionId) {
        log.info("Fetching nearby hotels for regionId: {}", regionId);

        if (regionId == null) {
            throw new GasStationBadRequestException("RegionId cannot be null");
        }

        List<GasStation> gasStations = gasStationsRepository.findByRegionId(regionId);

        if (gasStations.isEmpty()) {
            throw new GasStationNotFoundException(messageService.getMessage("gasstation.not.found"));
        }

        return gasStations.stream()
                .map(GasStation::toGasStationRequest)
                .toList();
    }



    @Override
    public GasStationsRequest createGasStation(GasStationsRequest gasStationsRequest) {
        if (gasStationsRequest.getId() != null) {
            throw new GasStationBadRequestException(messageService.getMessage("gasstation.id.must.null"));
        }

        validateDuplicate(null, gasStationsRequest.getName());
        gasStationsRequest.setName(gasStationsRequest.getName());
        gasStationsRequest.setDescription(gasStationsRequest.getDescription());
        gasStationsRequest.setDescriptionHy(gasStationsRequest.getDescriptionHy());
        gasStationsRequest.setDescriptionRu(gasStationsRequest.getDescriptionRu());
        gasStationsRequest.setNameHy(gasStationsRequest.getNameHy());
        gasStationsRequest.setNameRu(gasStationsRequest.getNameRu());
        try{
            RegionRequest region = regionService.getRegionById(gasStationsRequest.getRegionId());
            gasStationsRequest.setRegionId(region.getId());
        } catch (Exception e) {
            throw new GasStationApiException(messageService.getMessage("region.not.found"));
        }
        try{
            PhotoRequest photo = photoService.getPhotoById(gasStationsRequest.getPhotoId());
            gasStationsRequest.setPhotoId(photo.getId());
        }catch (Exception e){
            throw new GasStationApiException("Photo not found");
        }


        GasStation gasStations = new GasStation(gasStationsRequest);

        try {
            gasStations = gasStationsRepository.save(gasStations);
        } catch (Exception e) {
            throw new GasStationApiException(messageService.getMessage("gasstation.creating.error"), e);
        }

        GasStationsRequest newGasStation = gasStations.toGasStationRequest();

        return newGasStation;
    }

    @Override
    public GasStationsRequest updateGasStation(UUID id, GasStationsRequest gasStationsRequest) {
        GasStation gasStations = gasStationsRepository.findById(id)
                .orElseThrow(() -> new GasStationNotFoundException(messageService.getMessage("gasstation.not.found")));

        gasStations.setName(gasStationsRequest.getName());
        gasStations.setDescription(gasStationsRequest.getDescription());
        gasStations.setDescriptionHy(gasStationsRequest.getDescriptionHy());
        gasStations.setDescriptionRu(gasStationsRequest.getDescriptionRu());
        gasStations.setNameHy(gasStationsRequest.getNameHy());
        gasStations.setNameRu(gasStationsRequest.getNameRu());
        try{
            PhotoRequest photo = photoService.getPhotoById(gasStationsRequest.getPhotoId());
            gasStations.setPhotoId(photo.getId());
        }catch (Exception e){
            throw new GasStationApiException("Photo not found");
        }
        try{
            RegionRequest region = regionService.getRegionById(gasStationsRequest.getRegionId());
            gasStations.setRegionId(region.getId());
        } catch (Exception e) {
            throw new GasStationApiException(messageService.getMessage("region.not.found"));
        }

        try {
            GasStation updated = gasStationsRepository.save(gasStations);
            return updated.toGasStationRequest();
        } catch (Exception e) {
            throw new GasStationApiException(messageService.getMessage("gasstation.updating.error"), e);
        }
    }

    @Override
    public void deleteGasStation(UUID id) {
        gasStationsRepository.findById(id).orElseThrow(() ->
                new GasStationNotFoundException(messageService.getMessage("gasstation.not.found")));
        try {
            gasStationsRepository.deleteById(id);
        } catch (Exception e) {
            throw new GasStationApiException(messageService.getMessage("gasstation.deleting.error"), e);
        }

    }

    private void validateDuplicate(UUID id, String name) {
        Optional<GasStation> existing = gasStationsRepository.findByName(name);

        if (existing.isPresent()) {
            GasStation gasStations = existing.get();

            if (!gasStations.getId().equals(id)) {
                throw new GasStationAlreadyExistsException(
                        messageService.getMessage("gasstation.already.exists")
                );
            }
        }
    }
}
