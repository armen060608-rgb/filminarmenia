package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.FoodPlacesRequest;
import com.registration.request.NearbyHotelsRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.FoodPlacesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.reflections.Reflections.log;

@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.FOOD_PLACES)
public class FoodPlaceController {

    @Autowired
    private FoodPlacesService foodPlacesService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getFoodPlaces")
    public List<FoodPlacesRequest> getFoodPlaces() {
        return foodPlacesService.getFoodPlaces();
    }

    @GetMapping("/getFoodPlacess")
    public List<FoodPlacesRequest> getFoodPlacess() {
        return foodPlacesService.getFoodPlacess();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getFoodPlaceById/{id}")
    public FoodPlacesRequest getFoodPlaceById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return foodPlacesService.getFoodPlaceById(id);
    }


    @Operation(summary = "Get nearby food place by region ID.")
    @GetMapping("/getNearbyFoodPlaceByRegion/{regionId}")
    public List<FoodPlacesRequest> getFoodPlaceByRegion(@PathVariable UUID regionId) {
        log.info("Received regionId: {}", regionId);
        return foodPlacesService.getFoodPlaceByRegion(regionId);
    }



    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create food place with specified parameters.")
    @PostMapping("/createFoodPlace")
    @RequiredAdminUser
    public FoodPlacesRequest createFoodPlace(@RequestBody @Valid FoodPlacesRequest foodPlacesRequest) {
        log.info("received request to food place user");
        FoodPlacesRequest foodPlace = foodPlacesService.createFoodPlace(foodPlacesRequest);
        log.info("food place created successfully");
        return foodPlace;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @PutMapping("/updateFoodPlace/{id}")
    @RequiredAdminUser
    public FoodPlacesRequest updateFoodPlace(@PathVariable UUID id, @RequestBody FoodPlacesRequest foodPlacesRequest) {
        return foodPlacesService.updateFoodPlace(id, foodPlacesRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @DeleteMapping("/deleteFoodPlace/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFoodPlace(@PathVariable UUID id) {
        foodPlacesService.deleteFoodPlace(id);
    }
}