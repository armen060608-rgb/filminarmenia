package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.RoadQualityRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.RoadQualityService;
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
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.ROAD_QUALITIES)
public class RoadQualityController {

    @Autowired
    private RoadQualityService roadQualityService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getAllRoadQualities")
    private List<RoadQualityRequest> getAllRoadQualities() {
        return roadQualityService.getAllRoadQualities();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getRoadQualityById/{id}")
    public RoadQualityRequest getRoadQualityById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return roadQualityService.getRoadQualityById(id);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create road quality with specified parameters.")
    @PostMapping("/createRoadQuality")
    @RequiredAdminUser
    public RoadQualityRequest createRoadQuality(@RequestBody @Valid RoadQualityRequest roadQualityRequest) {
        log.info("received request to road quality user");
        RoadQualityRequest roadQuality = roadQualityService.createRoadQuality(roadQualityRequest);
        log.info("Road quality created successfully");
        return roadQuality;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @PutMapping("/updateRoadQuality/{id}")
    @RequiredAdminUser
    public RoadQualityRequest updateRoadQuality(@PathVariable UUID id, @RequestBody RoadQualityRequest roadQualityRequest) {
        return roadQualityService.updateRoadQuality(id, roadQualityRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @DeleteMapping("/deleteRoadQuality/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoadQuality(@PathVariable UUID id) {
        roadQualityService.deleteRoadQuality(id);
    }
}