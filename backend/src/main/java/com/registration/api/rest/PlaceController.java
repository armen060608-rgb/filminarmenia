package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.PlaceRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.reflections.Reflections.log;

@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.PLACES)
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getAllPlaces")
    public List<PlaceRequest> getAllPlaces() {
        return placeService.getAllPlaces();
    }


    @GetMapping("/getAllPlacess")
    public List<PlaceRequest> getAllPlacess() {
        return placeService.getAllPlacess();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create place with specified parameters.")
    @PostMapping("/createPlace")
    @RequiredAdminUser
    public PlaceRequest createPlace(@RequestBody @Valid PlaceRequest placeRequest) {
        log.info("received request to climate zone user");
        PlaceRequest place = placeService.createPlace(placeRequest);
        log.info("Climate zone created successfully");
        return place;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @PutMapping("/updatePlace/{id}")
    @RequiredAdminUser
    public PlaceRequest updatePlace(@PathVariable UUID id, @RequestBody PlaceRequest placeRequest) {
        return placeService.updatePlace(id, placeRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @DeleteMapping("/deletePlace/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlace(@PathVariable UUID id) {
        placeService.deletePlace(id);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getPlaceById/{id}")
    public PlaceRequest getPlaceById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return placeService.getPlaceById(id);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getRecommendedPlaces")
    public List<PlaceRequest> getRecommendedPlaces() {
        return placeService.getRecommendedPlaces();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/filterPlaces")
    public ResponseEntity<List<PlaceRequest>> filterPlaces(
            @RequestParam(required = false) UUID placeTypeId,
            @RequestParam(required = false) UUID countryId,
            @RequestParam(required = false) UUID roadQualityId,
            @RequestParam(required = false) UUID nearbyHotelsId,
            @RequestParam(required = false) UUID nearbyHospitalsId,
            @RequestParam(required = false) UUID nearbyPharmaciesId,
            @RequestParam(required = false) UUID gasStationsId,
            @RequestParam(required = false) UUID foodPlacesId,
            @RequestParam(required = false) UUID climateZoneId
    ) {
        List<PlaceRequest> filteredPlaces = placeService.searchPlaces(
                placeTypeId,
                countryId,
                roadQualityId,
                nearbyHotelsId,
                nearbyHospitalsId,
                nearbyPharmaciesId,
                gasStationsId,
                foodPlacesId,
                climateZoneId
        );

        return ResponseEntity.ok()
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .body(filteredPlaces);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/search-by-keywords")
    public ResponseEntity<List<PlaceRequest>> searchByKeywords(@RequestParam List<String> keywords) {
        List<PlaceRequest> results = placeService.searchPlacesByKeywords(keywords);
        return ResponseEntity.ok(results);
    }
}