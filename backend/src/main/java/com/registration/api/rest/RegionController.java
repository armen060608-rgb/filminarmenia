package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.RegionRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.RegionService;
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
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.REGIONS)
public class RegionController {

    @Autowired
    private RegionService regionService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getAllRegions")
    public List<RegionRequest> getAllRegions() {
        return regionService.getAllRegions();
    }

    @GetMapping("/getAllRegionss")
    public List<RegionRequest> getAllRegionss() {
        return regionService.getAllRegionss();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getRegionById/{id}")
    public RegionRequest getRegionById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return regionService.getRegionById(id);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create region with specified parameters.")
    @PostMapping("/createRegion")
    @RequiredAdminUser
    public RegionRequest createRegion(@RequestBody @Valid RegionRequest regionRequest) {
        log.info("received request to Region user");
        RegionRequest region = regionService.createRegion(regionRequest);
        log.info("Region created successfully");
        return region;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @PutMapping("/updateRegion/{id}")
    @RequiredAdminUser
    public RegionRequest updateRegion(@PathVariable UUID id, @RequestBody RegionRequest regionRequest) {
        return regionService.updateRegion(id, regionRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @DeleteMapping("/deleteRegion/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRegion(@PathVariable UUID id) {
        regionService.deleteRegion(id);
    }
}