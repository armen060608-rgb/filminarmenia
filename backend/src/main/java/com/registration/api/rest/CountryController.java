package com.registration.api.rest;

import com.registration.constants.RoutConstants;
import com.registration.request.CountryRequest;
import com.registration.security.RequiredAdminUser;
import com.registration.service.CountryService;
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
@RequestMapping(RoutConstants.BASE_URL + "${platform.version}" + RoutConstants.COUNTRIES)
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getAllCountries")
    public List<CountryRequest> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/getAllCountriess")
    public List<CountryRequest> getAllCountriess() {
        return countryService.getAllCountriess();
    }


    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @GetMapping("/getCountryById/{id}")
    public CountryRequest getCountryById(@PathVariable UUID id) {
        System.out.println("Received ID: " + id);
        return countryService.getCountryById(id);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @Operation(summary = "Create country with specified parameters.")
    @PostMapping("/createCountry")
    @RequiredAdminUser
    public CountryRequest createCountry(@RequestBody @Valid CountryRequest countryRequest) {
        log.info("received request to country user");
        CountryRequest country = countryService.createCountry(countryRequest);
        log.info("Country created successfully");
        return country;
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @PutMapping("/updateCountry/{id}")
    @RequiredAdminUser
    public CountryRequest updateCountry(@PathVariable UUID id, @RequestBody CountryRequest countryRequest) {
        return countryService.updateCountry(id, countryRequest);
    }

    @Parameter(
            name = "Accept-Language",
            in = ParameterIn.HEADER,
            description = "Language preference (en, hy, ru)",
            required = false,
            schema = @Schema(type = "string", defaultValue = "en", allowableValues = {"en", "hy", "ru"})
    )
    @DeleteMapping("/deleteCountry/{id}")
    @RequiredAdminUser
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCountry(@PathVariable UUID id) {
        countryService.deleteCountry(id);
    }
}