package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.Photo360Request;
import com.registration.request.PhotoRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.PhotoService;
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
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.PHOTO)
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getPhotos")
    public List<PhotoRequest> getPhotos() {
        return photoService.getPhotos();
    }

    @GetMapping("/getPhotoss")
    public List<PhotoRequest> getPhotoss() {
        return photoService.getPhotoss();
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getPhotoById/{id}")
    public PhotoRequest getPhotoById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return photoService.getPhotoById(id);
    }
    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create photo pharmacy with specified parameters.")
    @PostMapping("/createPhoto")
    @RequiredAdminUser
    public PhotoRequest createPhoto(@RequestBody @Valid PhotoRequest photoRequest) {
        log.info("received request to photo");
        PhotoRequest photoRequest1 = photoService.createPhoto(photoRequest);
        log.info("Photo created successfully");
        return photoRequest;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )

    @PutMapping("/updatePhoto/{id}")
    @RequiredAdminUser
    public PhotoRequest updatePhoto(@PathVariable UUID id, @RequestBody PhotoRequest photoRequest) {
        return photoService.updatePhoto(id, photoRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )

    @DeleteMapping("/deletePhoto/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@PathVariable UUID id) {
        photoService.deletePhoto(id);
    }
}