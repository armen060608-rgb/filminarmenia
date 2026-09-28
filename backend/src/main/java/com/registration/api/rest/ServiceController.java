package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.ServiceRequest;
import com.registration.service.ServiceService;
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
@RequestMapping(RoutConstants.SERVICES)
@Tag(name = "Service Controller", description = "CRUD operations for services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;


    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getAllServices")
    public ResponseEntity<List<ServiceRequest>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/getServiceById/{id}")
    public ResponseEntity<ServiceRequest> getServiceById(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @GetMapping("/getAllServicess")
    public ResponseEntity<List<ServiceRequest>> getAllServicess() {
        return ResponseEntity.ok(serviceService.getAllServicess());
    }

    @PostMapping("/createService")
    public ResponseEntity<ServiceRequest> createService(
            @RequestBody ServiceRequest serviceRequest) {
        return ResponseEntity.ok(serviceService.createService(serviceRequest));
    }

    @PutMapping("/updateService/{id}")
    public ResponseEntity<ServiceRequest> updateService(
            @PathVariable UUID id,
            @RequestBody ServiceRequest serviceRequest) {
        return ResponseEntity.ok(serviceService.updateService(id, serviceRequest));
    }

    @DeleteMapping("/deleteService/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable UUID id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}