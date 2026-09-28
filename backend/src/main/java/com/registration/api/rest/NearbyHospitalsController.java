package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.NearbyHospitalsRequest;
import com.registration.request.NearbyHotelsRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.NearbyHospitalsService;
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
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.NEARBY_HOSPITALS)
public class NearbyHospitalsController {

    @Autowired
    private NearbyHospitalsService nearbyHospitalsService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getNearbyHospitals")
    public List<NearbyHospitalsRequest> getNearbyHospitals() {
        return nearbyHospitalsService.getNearbyHospitals();
    }


    @GetMapping("/getNearbyHospitalss")
    public List<NearbyHospitalsRequest> getNearbyHospitalss() {
        return nearbyHospitalsService.getNearbyHospitalss();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getNearbyHospitalById/{id}")
    public NearbyHospitalsRequest getNearbyHospitalById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return nearbyHospitalsService.getNearbyHospitalById(id);
    }


    @Operation(summary = "Get nearby hospitals by region ID.")
    @GetMapping("/getNearbyHospitalsByRegion/{regionId}")
    public List<NearbyHospitalsRequest> getNearbyHospitalsByRegion(@PathVariable UUID regionId) {
        log.info("Received regionId: {}", regionId);
        return nearbyHospitalsService.getNearbyHospitalsByRegion(regionId);
    }


    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create nearby hospital with specified parameters.")
    @PostMapping("/createNearbyHospital")
    @RequiredAdminUser
    public NearbyHospitalsRequest createNearbyHospital(@RequestBody @Valid NearbyHospitalsRequest nearbyHospitalsRequest) {
        log.info("received request to nearby hospital user");
        NearbyHospitalsRequest nearbyHospitals = nearbyHospitalsService.createNearbyHospital(nearbyHospitalsRequest);
        log.info("nearby hospital created successfully");
        return nearbyHospitals;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @PutMapping("/updateNearbyHospital/{id}")
    @RequiredAdminUser
    public NearbyHospitalsRequest updateNearbyHospital(@PathVariable UUID id, @RequestBody NearbyHospitalsRequest nearbyHospitalsRequest) {
        return nearbyHospitalsService.updateNearbyHospital(id, nearbyHospitalsRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @DeleteMapping("/deleteNearbyHospital/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNearbyHospital(@PathVariable UUID id) {
        nearbyHospitalsService.deleteNearbyHospital(id);
    }
}