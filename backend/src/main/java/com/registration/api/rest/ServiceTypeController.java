package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.ServiceTypeRequest;
import com.registration.service.ServiceTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RoutConstants.SERVICE_TYPES)
@Tag(name = "Service Type Controller", description = "CRUD operations for service types")
public class ServiceTypeController {

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getAllServiceTypes")
    public ResponseEntity<List<ServiceTypeRequest>> getAllServiceTypes() {
        return ResponseEntity.ok(serviceTypeService.getAllServiceTypes());
    }

    @GetMapping("/getServiceTypeById/{id}")
    public ResponseEntity<ServiceTypeRequest> getServiceTypeById(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceTypeService.getServiceTypeById(id));
    }

    @GetMapping("/getAllServiceTypess")
    public ResponseEntity<List<ServiceTypeRequest>> getAllServiceTypess() {
        return ResponseEntity.ok(serviceTypeService.getAllServiceTypess());
    }

    @PostMapping("/createServiceType")
    public ResponseEntity<ServiceTypeRequest> createServiceType(
            @RequestBody ServiceTypeRequest serviceTypeRequest) {
        return ResponseEntity.ok(serviceTypeService.createServiceType(serviceTypeRequest));
    }

    @PutMapping("/updateServiceType/{id}")
    public ResponseEntity<ServiceTypeRequest> updateServiceType(
            @PathVariable UUID id,
            @RequestBody ServiceTypeRequest serviceTypeRequest) {
        return ResponseEntity.ok(serviceTypeService.updateServiceType(id, serviceTypeRequest));
    }

    @DeleteMapping("/deleteServiceType/{id}")
    public ResponseEntity<Void> deleteServiceType(@PathVariable UUID id) {
        serviceTypeService.deleteServiceType(id);
        return ResponseEntity.noContent().build();
    }
}