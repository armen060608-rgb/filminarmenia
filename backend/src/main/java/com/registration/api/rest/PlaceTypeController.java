package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.PlaceTypeRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.PlaceTypeService;
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
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.PLACE_TYPES)
public class PlaceTypeController {

    @Autowired
    private PlaceTypeService placeTypeService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getAllPlaceTypes")
    public List<PlaceTypeRequest> getAllPlaceTypes() {
        return placeTypeService.getAllPlaceTypes();
    }


    @GetMapping("/getAllPlaceTypess")
    public List<PlaceTypeRequest> getAllPlaceTypess() {
        return placeTypeService.getAllPlaceTypess();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getPlaceTypeById/{id}")
    public PlaceTypeRequest getPlaceTypeById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return placeTypeService.getPlaceTypeById(id);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create place type with specified parameters.")
    @PostMapping("/createPlaceType")
    @RequiredAdminUser
    public PlaceTypeRequest createPlaceType(@RequestBody @Valid PlaceTypeRequest placeTypeRequest) {
        log.info("received request to place type user");
        PlaceTypeRequest placeType = placeTypeService.createPlaceType(placeTypeRequest);
        log.info("Place type created successfully");
        return placeType;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @PutMapping("/updatePlaceType/{id}")
    @RequiredAdminUser
    public PlaceTypeRequest updatePlaceType(@PathVariable UUID id, @RequestBody PlaceTypeRequest placeTypeRequest) {
        return placeTypeService.updatePlaceType(id, placeTypeRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @DeleteMapping("/deletePlaceType/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlaceType(@PathVariable UUID id) {
        placeTypeService.deletePlaceType(id);
    }
}