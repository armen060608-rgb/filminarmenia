package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.TransportTypeRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.TransportTypeService;
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
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.TRANSPORT_TYPES)
public class TransportTypeController {

    @Autowired
    private TransportTypeService transportTypeService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getALlTransportTypes")
    public List<TransportTypeRequest> getAllTransportTypes() {
        return transportTypeService.getAllTransportTypes();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getTransportTypeById/{id}")
    public TransportTypeRequest getTransportTypeById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return transportTypeService.getTransportTypeById(id);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create transport type with specified parameters.")
    @PostMapping("/createTransportType")
    @RequiredAdminUser
    public TransportTypeRequest createTransportType(@RequestBody @Valid TransportTypeRequest transportTypeRequest) {
        log.info("received request to transport type user");
        TransportTypeRequest transportType = transportTypeService.createTransportType(transportTypeRequest);
        log.info("Transport type created successfully");
        return transportType;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @PutMapping("/updateTransportType/{id}")
    @RequiredAdminUser
    public TransportTypeRequest updateTransportType(@PathVariable UUID id, @RequestBody TransportTypeRequest transportTypeRequest) {
        return transportTypeService.updateTransportType(id, transportTypeRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @DeleteMapping("/deleteTransportType/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransportType(@PathVariable UUID id) {
        transportTypeService.deleteTransportType(id);
    }
}