package com.registration.ex;

import com.registration.helper.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @Autowired
    private MessageService messageService;

    // Helper method to create headers with UTF-8
    private HttpHeaders createUtf8Headers() {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
        headers.setContentType(mediaType);
        return headers;
    }

    // Handle User Exceptions
    @ExceptionHandler(com.registration.ex.userexceptions.UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(
            com.registration.ex.userexceptions.UserNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(com.registration.ex.userexceptions.UserAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleUserAlreadyExistException(
            com.registration.ex.userexceptions.UserAlreadyExistException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.userexceptions.UserBadRequestException.class)
    public ResponseEntity<ErrorDetails> handleUserBadRequestException(
            com.registration.ex.userexceptions.UserBadRequestException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.registration.ex.userexceptions.UserApiException.class)
    public ResponseEntity<ErrorDetails> handleUserApiException(
            com.registration.ex.userexceptions.UserApiException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Climate Zone Exceptions
    @ExceptionHandler(com.registration.ex.climatezoneexception.ClimateZoneNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleClimateZoneNotFoundException(
            com.registration.ex.climatezoneexception.ClimateZoneNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(com.registration.ex.climatezoneexception.ClimateZoneAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleClimateZoneAlreadyExistException(
            com.registration.ex.climatezoneexception.ClimateZoneAlreadyExistException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.climatezoneexception.ClimateZoneBadRequestException.class)
    public ResponseEntity<ErrorDetails> handleClimateZoneBadRequestException(
            com.registration.ex.climatezoneexception.ClimateZoneBadRequestException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.registration.ex.climatezoneexception.ClimateZoneApiException.class)
    public ResponseEntity<ErrorDetails> handleClimateZoneApiException(
            com.registration.ex.climatezoneexception.ClimateZoneApiException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Country Exceptions
    @ExceptionHandler(com.registration.ex.countryexception.CountryNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCountryNotFoundException(
            com.registration.ex.countryexception.CountryNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    // Handle Transport Type Exceptions
    @ExceptionHandler(com.registration.ex.transporttypeexcpetion.TransportTypeNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleTransportTypeNotFoundException(
            com.registration.ex.transporttypeexcpetion.TransportTypeNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(com.registration.ex.transporttypeexcpetion.TransportTypeAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleTransportTypeAlreadyExistsException(
            com.registration.ex.transporttypeexcpetion.TransportTypeAlreadyExistsException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.transporttypeexcpetion.TransportTypeApiException.class)
    public ResponseEntity<ErrorDetails> handleTransportTypeApiException(
            com.registration.ex.transporttypeexcpetion.TransportTypeApiException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Place Exceptions
    @ExceptionHandler(com.registration.ex.placeexceptions.PlaceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlePlaceNotFoundException(
            com.registration.ex.placeexceptions.PlaceNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(com.registration.ex.placeexceptions.PlaceApiException.class)
    public ResponseEntity<ErrorDetails> handlePlaceApiException(
            com.registration.ex.placeexceptions.PlaceApiException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Food Place Exceptions
    @ExceptionHandler(com.registration.ex.foodplacesexception.FoodPlaceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleFoodPlaceNotFoundException(
            com.registration.ex.foodplacesexception.FoodPlaceNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    // Handle Gas Station Exceptions
    @ExceptionHandler(com.registration.ex.gasstationexception.GasStationNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleGasStationNotFoundException(
            com.registration.ex.gasstationexception.GasStationNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    // Handle Region Exceptions
    @ExceptionHandler(com.registration.ex.regionexception.RegionNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleRegionNotFoundException(
            com.registration.ex.regionexception.RegionNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    // Handle Nearby Hotels Exceptions
    @ExceptionHandler(com.registration.ex.nearbyhotelsexception.NearbyHotelsNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNearbyHotelsNotFoundException(
            com.registration.ex.nearbyhotelsexception.NearbyHotelsNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    // Handle Nearby Hospitals Exceptions
    @ExceptionHandler(com.registration.ex.nearbyhospitalsexception.NearbyHospitalsNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNearbyHospitalsNotFoundException(
            com.registration.ex.nearbyhospitalsexception.NearbyHospitalsNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    // Handle Nearby Pharmacy Exceptions
    @ExceptionHandler(com.registration.ex.nearbypharmacyexception.NearbyPharmacyNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNearbyPharmacyNotFoundException(
            com.registration.ex.nearbypharmacyexception.NearbyPharmacyNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    // Handle Place Type Exceptions
    @ExceptionHandler(com.registration.ex.placetypeexception.PlaceTypeNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlePlaceTypeNotFoundException(
            com.registration.ex.placetypeexception.PlaceTypeNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    // Handle Road Quality Exceptions
    @ExceptionHandler(com.registration.ex.roadqualityexception.RoadQualityNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleRoadQualityNotFoundException(
            com.registration.ex.roadqualityexception.RoadQualityNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    // Handle Photo Exceptions
    @ExceptionHandler(com.registration.ex.photoexception.PhotoNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlePhotoNotFoundException(
            com.registration.ex.photoexception.PhotoNotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    // Handle Photo360 Exceptions
    @ExceptionHandler(com.registration.ex.photo360exception.Photo360NotFoundException.class)
    public ResponseEntity<ErrorDetails> handlePhoto360NotFoundException(
            com.registration.ex.photo360exception.Photo360NotFoundException ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    // Handle Validation Exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errors);

        return new ResponseEntity<>(response, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }


    // Handle Service Type Exceptions
    @ExceptionHandler(com.registration.ex.servicetypeexception.ServiceTypeNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleServiceTypeNotFoundException(
            com.registration.ex.servicetypeexception.ServiceTypeNotFoundException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(com.registration.ex.servicetypeexception.ServiceTypeAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleServiceTypeAlreadyExistException(
            com.registration.ex.servicetypeexception.ServiceTypeAlreadyExistException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.servicetypeexception.ServiceTypeBadRequestException.class)
    public ResponseEntity<ErrorDetails> handleServiceTypeBadRequestException(
            com.registration.ex.servicetypeexception.ServiceTypeBadRequestException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.registration.ex.servicetypeexception.ServiceTypeApiException.class)
    public ResponseEntity<ErrorDetails> handleServiceTypeApiException(
            com.registration.ex.servicetypeexception.ServiceTypeApiException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Service Exceptions
    @ExceptionHandler(com.registration.ex.serviceexception.ServiceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleServiceNotFoundException(
            com.registration.ex.serviceexception.ServiceNotFoundException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(com.registration.ex.serviceexception.ServiceAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleServiceAlreadyExistException(
            com.registration.ex.serviceexception.ServiceAlreadyExistException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.serviceexception.ServiceBadRequestException.class)
    public ResponseEntity<ErrorDetails> handleServiceBadRequestException(
            com.registration.ex.serviceexception.ServiceBadRequestException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.registration.ex.serviceexception.ServiceApiException.class)
    public ResponseEntity<ErrorDetails> handleServiceApiException(
            com.registration.ex.serviceexception.ServiceApiException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Country Missing Exceptions
    @ExceptionHandler(com.registration.ex.countryexception.CountryAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleCountryAlreadyExistException(
            com.registration.ex.countryexception.CountryAlreadyExistException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.countryexception.CountryBadRequestException.class)
    public ResponseEntity<ErrorDetails> handleCountryBadRequestException(
            com.registration.ex.countryexception.CountryBadRequestException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.registration.ex.countryexception.CountryApiException.class)
    public ResponseEntity<ErrorDetails> handleCountryApiException(
            com.registration.ex.countryexception.CountryApiException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Food Place Missing Exceptions
    @ExceptionHandler(com.registration.ex.foodplacesexception.FoodPlaceAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleFoodPlaceAlreadyExistException(
            com.registration.ex.foodplacesexception.FoodPlaceAlreadyExistsException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.foodplacesexception.FoodPlacesBadRequestException.class)
    public ResponseEntity<ErrorDetails> handleFoodPlaceBadRequestException(
            com.registration.ex.foodplacesexception.FoodPlacesBadRequestException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.registration.ex.foodplacesexception.FoodPlaceApiException.class)
    public ResponseEntity<ErrorDetails> handleFoodPlaceApiException(
            com.registration.ex.foodplacesexception.FoodPlaceApiException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Gas Station Missing Exceptions
    @ExceptionHandler(com.registration.ex.gasstationexception.GasStationAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleGasStationAlreadyExistException(
            com.registration.ex.gasstationexception.GasStationAlreadyExistsException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.gasstationexception.GasStationBadRequestException.class)
    public ResponseEntity<ErrorDetails> handleGasStationBadRequestException(
            com.registration.ex.gasstationexception.GasStationBadRequestException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.registration.ex.gasstationexception.GasStationApiException.class)
    public ResponseEntity<ErrorDetails> handleGasStationApiException(
            com.registration.ex.gasstationexception.GasStationApiException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Region Missing Exceptions
    @ExceptionHandler(com.registration.ex.regionexception.RegionAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleRegionAlreadyExistException(
            com.registration.ex.regionexception.RegionAlreadyExistsException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.regionexception.RegionBadRequestException.class)
    public ResponseEntity<ErrorDetails> handleRegionBadRequestException(
            com.registration.ex.regionexception.RegionBadRequestException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.registration.ex.regionexception.RegionApiException.class)
    public ResponseEntity<ErrorDetails> handleRegionApiException(
            com.registration.ex.regionexception.RegionApiException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Nearby Hotels Missing Exceptions
    @ExceptionHandler(com.registration.ex.nearbyhotelsexception.NearbyHotelsAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleNearbyHotelsAlreadyExistException(
            com.registration.ex.nearbyhotelsexception.NearbyHotelsAlreadyExistsException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.nearbyhotelsexception.NearbyHotelsBadRequestException.class)
    public ResponseEntity<ErrorDetails> handleNearbyHotelsBadRequestException(
            com.registration.ex.nearbyhotelsexception.NearbyHotelsBadRequestException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.registration.ex.nearbyhotelsexception.NearbyHotelsApiException.class)
    public ResponseEntity<ErrorDetails> handleNearbyHotelsApiException(
            com.registration.ex.nearbyhotelsexception.NearbyHotelsApiException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Nearby Hospitals Missing Exceptions
    @ExceptionHandler(com.registration.ex.nearbyhospitalsexception.NearbyHospitalsAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleNearbyHospitalsAlreadyExistException(
            com.registration.ex.nearbyhospitalsexception.NearbyHospitalsAlreadyExistsException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.nearbyhospitalsexception.NearbyHospitalsBadRequestException.class)
    public ResponseEntity<ErrorDetails> handleNearbyHospitalsBadRequestException(
            com.registration.ex.nearbyhospitalsexception.NearbyHospitalsBadRequestException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.registration.ex.nearbyhospitalsexception.NearbyHospitalsApiException.class)
    public ResponseEntity<ErrorDetails> handleNearbyHospitalsApiException(
            com.registration.ex.nearbyhospitalsexception.NearbyHospitalsApiException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Nearby Pharmacy Missing Exceptions
    @ExceptionHandler(com.registration.ex.nearbypharmacyexception.NearbyPharmacyAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleNearbyPharmacyAlreadyExistException(
            com.registration.ex.nearbypharmacyexception.NearbyPharmacyAlreadyExistsException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.nearbypharmacyexception.NearbyPharmacyBadRequestException.class)
    public ResponseEntity<ErrorDetails> handleNearbyPharmacyBadRequestException(
            com.registration.ex.nearbypharmacyexception.NearbyPharmacyBadRequestException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.registration.ex.nearbypharmacyexception.NearbyPharmacyApiException.class)
    public ResponseEntity<ErrorDetails> handleNearbyPharmacyApiException(
            com.registration.ex.nearbypharmacyexception.NearbyPharmacyApiException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle Place Type Missing Exceptions
    @ExceptionHandler(com.registration.ex.placetypeexception.PlaceTypeAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handlePlaceTypeAlreadyExistException(
            com.registration.ex.placetypeexception.PlaceTypeAlreadyExistsException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(com.registration.ex.placetypeexception.PlaceTypeBadRequestException.class)
    public ResponseEntity<ErrorDetails> handlePlaceTypeBadRequestException(
            com.registration.ex.placetypeexception.PlaceTypeBadRequestException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.registration.ex.placetypeexception.PlaceTypeApiException.class)
    public ResponseEntity<ErrorDetails> handlePlaceTypeApiException(
            com.registration.ex.placetypeexception.PlaceTypeApiException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }





    @ExceptionHandler(com.registration.ex.roadqualityexception.RoadQualityApiException.class)
    public ResponseEntity<ErrorDetails> handleRoadQualityApiException(
            com.registration.ex.roadqualityexception.RoadQualityApiException ex,
            WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle All Other Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception ex,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                messageService.getMessage("error.server"),
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorDetails, createUtf8Headers(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}