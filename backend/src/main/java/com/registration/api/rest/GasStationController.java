package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.GasStationsRequest;
import com.registration.request.NearbyHotelsRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.GasStationService;
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
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.GAS_STATION)
public class GasStationController {

    @Autowired
    private GasStationService gasStationService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getGasStations")
    public List<GasStationsRequest> getGasStations() {
        return gasStationService.getGasStations();
    }

    @GetMapping("/getGasStationss")
    public List<GasStationsRequest> getGasStationss() {
        return gasStationService.getGasStationss();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getGasStationById/{id}")
    public GasStationsRequest getGasStationById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return gasStationService.getGasStationById(id);
    }


    @Operation(summary = "Get gas station by region ID.")
    @GetMapping("/getGasStationByRegion/{regionId}")
    public List<GasStationsRequest> getGasStationByRegion(@PathVariable UUID regionId) {
        log.info("Received regionId: {}", regionId);
        return gasStationService.getGasStationByRegion(regionId);
    }



    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create gas station with specified parameters.")
    @PostMapping("/createGasStation")
    @RequiredAdminUser
    public GasStationsRequest createGasStation(@RequestBody @Valid GasStationsRequest gasStationsRequest) {
        log.info("received request to gas station user");
        GasStationsRequest gasStations = gasStationService.createGasStation(gasStationsRequest);
        log.info("gas station created successfully");
        return gasStations;
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
    public GasStationsRequest updateFoodPlace(@PathVariable UUID id, @RequestBody GasStationsRequest gasStationsRequest) {
        return gasStationService.updateGasStation(id, gasStationsRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @DeleteMapping("/deleteGasStation/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGasStation(@PathVariable UUID id) {
        gasStationService.deleteGasStation(id);
    }
}