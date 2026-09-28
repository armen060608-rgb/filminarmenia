package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.NearbyHotelsRequest;
import com.registration.request.NearbyPharmaciesRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.NearbyPharmaciesService;
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
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.NEARBY_PHARMACY)
public class NearbyPharmacyController {

    @Autowired
    private NearbyPharmaciesService nearbyPharmaciesService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )

    @GetMapping("/getNearbyPharmacies")
    public List<NearbyPharmaciesRequest> getNearbyPharmacies() {
        return nearbyPharmaciesService.getNearbyPharmacies();
    }


    @GetMapping("/getNearbyPharmaciess")
    public List<NearbyPharmaciesRequest> getNearbyPharmaciess() {
        return nearbyPharmaciesService.getNearbyPharmaciess();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )

    @GetMapping("/getNearbyPharmacyById/{id}")
    public NearbyPharmaciesRequest getNearbyPharmacyById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return nearbyPharmaciesService.getNearbyPharmacyById(id);
    }


    @Operation(summary = "Get nearby pharmacy by region ID.")
    @GetMapping("/getNearbyPharmacyByRegion/{regionId}")
    public List<NearbyPharmaciesRequest> getNearbyPharmacyByRegion(@PathVariable UUID regionId) {
        log.info("Received regionId: {}", regionId);
        return nearbyPharmaciesService.getNearbyPharmacyByRegion(regionId);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create nearby pharmacy with specified parameters.")
    @PostMapping("/createNearbyPharmacy")
    @RequiredAdminUser
    public NearbyPharmaciesRequest createNearbyPharmacy(@RequestBody @Valid NearbyPharmaciesRequest nearbyPharmaciesRequest) {
        log.info("received request to nearby pharmacy user");
        NearbyPharmaciesRequest nearbyPharmacy = nearbyPharmaciesService.createNearbyPharmacy(nearbyPharmaciesRequest);
        log.info("Nearby Pharmacy created successfully");
        return nearbyPharmacy;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )

    @PutMapping("/updateNearbyPharmacy/{id}")
    @RequiredAdminUser
    public NearbyPharmaciesRequest updateNearbyPharmacy(@PathVariable UUID id, @RequestBody NearbyPharmaciesRequest nearbyPharmaciesRequest) {
        return nearbyPharmaciesService.updateNearbyPharmacy(id, nearbyPharmaciesRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )

    @DeleteMapping("/deleteNearbyPharmacy/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNearbyPharmacy(@PathVariable UUID id) {
        nearbyPharmaciesService.deleteNearbyPharmacy(id);
    }
}