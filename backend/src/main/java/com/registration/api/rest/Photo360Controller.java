package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.NearbyPharmaciesRequest;
import com.registration.request.Photo360Request;
import com.registration.security.RequiredAdminUser;
import com.registration.service.Photo360Service;
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
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.PHOTO360)
public class Photo360Controller {

    @Autowired
    private Photo360Service photo360Service;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getPhoto360")
    public List<Photo360Request> getPhoto360() {
        return photo360Service.getPhoto360();
    }

    @GetMapping("/getPhoto360s")
    public List<Photo360Request> getPhoto360s() {
        return photo360Service.getPhoto360s();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getPhoto360ById/{id}")
    public Photo360Request getPhoto360ById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return photo360Service.getPhoto360ById(id);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create photo 360 pharmacy with specified parameters.")
    @PostMapping("/createPhoto360")
    @RequiredAdminUser
    public Photo360Request createPhoto360(@RequestBody @Valid Photo360Request photo360Request) {
        log.info("received request to photo360");
        Photo360Request photo360Request1 = photo360Service.createPhoto360(photo360Request);
        log.info("Photo 360 created successfully");
        return photo360Request;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )

    @PutMapping("/updatePhoto360/{id}")
    @RequiredAdminUser
    public Photo360Request updatePhoto360(@PathVariable UUID id, @RequestBody Photo360Request photo360Request) {
        return photo360Service.updatePhoto360(id, photo360Request);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )

    @DeleteMapping("/deletePhoto360/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto360(@PathVariable UUID id) {
        photo360Service.deletePhoto360(id);
    }
}


