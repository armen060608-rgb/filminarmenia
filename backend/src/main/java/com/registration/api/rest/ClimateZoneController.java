package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.ClimateZoneRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.ClimateZoneService;
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
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.CLIMATE_ZONES)
public class ClimateZoneController {

    @Autowired
    private ClimateZoneService climateZoneService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getAllClimateZones")
    public List<ClimateZoneRequest> getAllClimateZones() {
        return climateZoneService.getClimateZones();
    }

    @GetMapping("/getAllClimateZoness")
    public List<ClimateZoneRequest> getAllClimateZoness() {
        return climateZoneService.getClimateZoness();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getClimateZoneById/{id}")
    public ClimateZoneRequest getClimatezoneById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return climateZoneService.getClimateZoneById(id);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create climate zone with specified parameters.")
    @PostMapping("/createClimateZone")
    @RequiredAdminUser
    public ClimateZoneRequest createClimateZone(@RequestBody @Valid ClimateZoneRequest climateZoneRequest) {
        log.info("received request to climate zone user");
        ClimateZoneRequest climateZone = climateZoneService.createClimateZone(climateZoneRequest);
        log.info("Climate zone created successfully");
        return climateZone;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @PutMapping("/updateClimateZone/{id}")
    @RequiredAdminUser
    public ClimateZoneRequest updateClimateZone(@PathVariable UUID id, @RequestBody ClimateZoneRequest climateZoneRequest) {
        return climateZoneService.updateClimateZone(id, climateZoneRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @DeleteMapping("/deleteClimateZone/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClimateZone(@PathVariable UUID id) {
        climateZoneService.deleteClimateZone(id);
    }
}