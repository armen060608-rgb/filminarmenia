package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.NearbyHotelsRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.NearbyHotelsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.NEARBY_HOTELS)
public class NearbyHotelsController {

    @Autowired
    private NearbyHotelsService nearbyHotelsService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getNearbyHotels")
    public List<NearbyHotelsRequest> getNearbyHotels() {
        return nearbyHotelsService.getNearbyHotels();
    }


    @GetMapping("/getNearbyHotelss")
    public List<NearbyHotelsRequest> getNearbyHotelss() {
        return nearbyHotelsService.getNearbyHotelss();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getNearbyHotelById/{id}")
    public NearbyHotelsRequest getNearbyHotelsById(@PathVariable UUID id) {
        log.info("Received ID: {}", id);
        return nearbyHotelsService.getNearbyHotelById(id);
    }


    @Operation(summary = "Get nearby hotels by region ID.")
    @GetMapping("/getNearbyHotelsByRegion/{regionId}")
    public List<NearbyHotelsRequest> getNearbyHotelsByRegion(@PathVariable UUID regionId) {
        log.info("Received regionId: {}", regionId);
        return nearbyHotelsService.getNearbyHotelsByRegion(regionId);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create nearby hotel with specified parameters.")
    @PostMapping("/createNearbyHotel")
    @RequiredAdminUser
    public NearbyHotelsRequest createNearbyHotel(@RequestBody @Valid NearbyHotelsRequest nearbyHotelsRequest) {
        log.info("received request to nearby hotel user");
        NearbyHotelsRequest nearbyHotels = nearbyHotelsService.createNearbyHotel(nearbyHotelsRequest);
        log.info("nearby hotel created successfully");
        return nearbyHotels;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Update nearby hotel by ID.")
    @PutMapping("/updateNearbyHotel/{id}")
    @RequiredAdminUser
    public NearbyHotelsRequest updateNearbyHotel(@PathVariable UUID id, @RequestBody NearbyHotelsRequest nearbyHotelsRequest) {
        return nearbyHotelsService.updateNearbyHotel(id, nearbyHotelsRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Delete nearby hotel by ID.")
    @DeleteMapping("/deleteNearbyHotel/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNearbyHotel(@PathVariable UUID id) {
        nearbyHotelsService.deleteNearbyHotel(id);
    }
}