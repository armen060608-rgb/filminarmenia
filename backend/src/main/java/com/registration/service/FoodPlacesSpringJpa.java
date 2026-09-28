package com.registration.service;

import com.registration.entity.placefinder.FoodPlaces;
import com.registration.entity.placefinder.NearbyHotels;
import com.registration.ex.foodplacesexception.FoodPlaceAlreadyExistsException;
import com.registration.ex.foodplacesexception.FoodPlaceApiException;
import com.registration.ex.foodplacesexception.FoodPlaceNotFoundException;
import com.registration.ex.foodplacesexception.FoodPlacesBadRequestException;
import com.registration.ex.gasstationexception.GasStationApiException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsBadRequestException;
import com.registration.ex.nearbyhotelsexception.NearbyHotelsNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.FoodPlacesRepository;
import com.registration.repository.RegionRepository;
import com.registration.request.FoodPlacesRequest;
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
public class FoodPlacesSpringJpa implements FoodPlacesService {

    @Autowired
    private FoodPlacesRepository foodPlacesRepository;
    @Autowired
    private RegionService regionService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private MessageService messageService;

    @Override
    public List<FoodPlacesRequest> getFoodPlaces() {
        try {
            return foodPlacesRepository.findAll()
                    .stream()
                    .map(FoodPlaces::toFoodPlacesRequest)
                    .toList();
        } catch (Exception e) {
            throw new FoodPlaceApiException("Problem during getting all food places", e);
        }
    }


    @Override
    public List<FoodPlacesRequest> getFoodPlacess() {
        try {
            return foodPlacesRepository.findAll()
                    .stream()
                    .map(FoodPlaces::toFoodPlacesRequestFull)
                    .toList();
        } catch (Exception e) {
            throw new FoodPlaceApiException("Problem during getting all food places", e);
        }
    }


    @Override
    public FoodPlacesRequest getFoodPlaceById(UUID id) {
        FoodPlaces foodPlaces = foodPlacesRepository.findById(id)
                .orElseThrow(() -> new FoodPlaceNotFoundException("Food place not found with given ID"));
        return foodPlaces.toFoodPlacesRequest();
    }

    @Override
    public List<FoodPlacesRequest> getFoodPlaceByRegion(UUID regionId) {
        log.info("Fetching nearby food place for regionId: {}", regionId);

        if (regionId == null) {
            throw new FoodPlacesBadRequestException("RegionId cannot be null");
        }

        List<FoodPlaces> foodPlaces = foodPlacesRepository.findByRegionId(regionId);

        if (foodPlaces.isEmpty()) {
            throw new FoodPlaceNotFoundException(messageService.getMessage("foodplaces.not.found"));
        }

        return foodPlaces.stream()
                .map(FoodPlaces::toFoodPlacesRequest)
                .toList();
    }



    @Override
    public FoodPlacesRequest createFoodPlace(FoodPlacesRequest foodPlacesRequest) {
        if (foodPlacesRequest.getId() != null) {
            throw new FoodPlacesBadRequestException("Gas station ID must be null");
        }

        validateDuplicate(null, foodPlacesRequest.getName());
        foodPlacesRequest.setName(foodPlacesRequest.getName());
        foodPlacesRequest.setDescription(foodPlacesRequest.getDescription());
        foodPlacesRequest.setDescriptionHy(foodPlacesRequest.getDescriptionHy());
        foodPlacesRequest.setDescriptionRu(foodPlacesRequest.getDescriptionRu());
        foodPlacesRequest.setNameHy(foodPlacesRequest.getNameHy());
        foodPlacesRequest.setNameRu(foodPlacesRequest.getNameRu());

        FoodPlaces foodPlaces = new FoodPlaces(foodPlacesRequest);
        try{
            RegionRequest region = regionService.getRegionById(foodPlacesRequest.getRegionId());
            foodPlacesRequest.setRegionId(region.getId());
        }catch (Exception e){
            throw new GasStationApiException("Region not found");
        }
        try{
            PhotoRequest photo = photoService.getPhotoById(foodPlacesRequest.getPhotoId());
            foodPlacesRequest.setPhotoId(photo.getId());
        }catch (Exception e){
            throw new GasStationApiException("Photo not found");
        }

        try {
            foodPlaces = foodPlacesRepository.save(foodPlaces);
        } catch (Exception e) {
            throw new FoodPlaceApiException("Problem during creating food place", e);
        }

        FoodPlacesRequest newFoodPlace = foodPlaces.toFoodPlacesRequest();

        return newFoodPlace;
    }
    @Override
    public FoodPlacesRequest updateFoodPlace(UUID id, FoodPlacesRequest foodPlacesRequest) {
        FoodPlaces foodPlaces = foodPlacesRepository.findById(id)
                .orElseThrow(() -> new FoodPlaceNotFoundException("Food place not found with given ID"));

        foodPlaces.setName(foodPlacesRequest.getName());
        foodPlaces.setDescription(foodPlacesRequest.getDescription());
        foodPlaces.setDescriptionHy(foodPlacesRequest.getDescriptionHy());
        foodPlaces.setDescriptionRu(foodPlacesRequest.getDescriptionRu());
        foodPlaces.setNameHy(foodPlacesRequest.getNameHy());
        foodPlaces.setNameRu(foodPlacesRequest.getNameRu());
        try{
            RegionRequest region = regionService.getRegionById(foodPlacesRequest.getRegionId());
            foodPlaces.setRegionId(region.getId());
        }catch (Exception e){
            throw new GasStationApiException("Region not found");
        }
        try{
            PhotoRequest photo = photoService.getPhotoById(foodPlacesRequest.getPhotoId());
            foodPlaces.setPhotoId(photo.getId());
        }catch (Exception e){
            throw new GasStationApiException("Photo not found");
        }
        try {
            FoodPlaces updated = foodPlacesRepository.save(foodPlaces);
            return updated.toFoodPlacesRequest();
        } catch (Exception e) {
            throw new FoodPlaceApiException("Problem during updating food place", e);
        }
    }

    @Override
    public void deleteFoodPlace(UUID id) {
        foodPlacesRepository.findById(id).orElseThrow(() ->
                new FoodPlaceNotFoundException("Food place not found with given ID"));
        try {
            foodPlacesRepository.deleteById(id);
        } catch (Exception e) {
            throw new FoodPlaceApiException("Problem during deleting food place", e);
        }

    }

    private void validateDuplicate(UUID id, String name) {
        Optional<FoodPlaces> existing = foodPlacesRepository.findByName(name);

        if (existing.isPresent()) {
            FoodPlaces foodPlaces= existing.get();

            if (!foodPlaces.getId().equals(id)) {
                throw new FoodPlaceAlreadyExistsException(
                        "Food place with name '" + name + "' already exists"
                );
            }
        }
    }

}
