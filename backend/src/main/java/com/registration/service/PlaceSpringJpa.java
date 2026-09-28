package com.registration.service;

import com.registration.entity.PlaceEntity;
import com.registration.entity.placefinder.*;
import com.registration.ex.climatezoneexception.ClimateZoneAlreadyExistException;
import com.registration.ex.placeexceptions.PlaceApiException;
import com.registration.ex.placeexceptions.PlaceBadRequestException;
import com.registration.ex.placeexceptions.PlaceNotFoundException;
import com.registration.helper.MessageService;
import com.registration.repository.*;
import com.registration.request.*;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PlaceSpringJpa implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private NearbyHotelsRepository nearbyHotelsRepository;
    @Autowired
    private NearbyHospitalsRepository nearbyHospitalsRepository;
    @Autowired
    private NearbyPharmaciesRepository nearbyPharmaciesRepository;
    @Autowired
    private GasStationsRepository gasStationsRepository;
    @Autowired
    private FoodPlacesRepository foodPlacesRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private PlaceKeywordRepository placeKeywordRepository;

    @Autowired
    private PlaceNearbyHotelRepository placeNearbyHotelRepository;
    @Autowired
    private PlaceNearbyHospitalRepository placeNearbyHospitalRepository;
    @Autowired
    private PlaceNearbyPharmacyRepository placeNearbyPharmacyRepository;
    @Autowired
    private PlaceGasStationRepository placeGasStationRepository;
    @Autowired
    private PlaceFoodPlaceRepository placeFoodPlaceRepository;
    @Autowired
    private PlacePhotoRepository placePhotoRepository;

    @Autowired
    private MessageService messageService;

    // ---------- read ----------
    @Override
    @Transactional
    public List<PlaceRequest> getAllPlaces() {
        try {
            return placeRepository.findAll().stream()
                    .map(this::buildPlaceRequest)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new PlaceApiException(messageService.getMessage("place.getting.all.error"), e);
        }
    }

    @Override
    @Transactional
    public List<PlaceRequest> getAllPlacess() {
        return getAllPlaces();
    }

    @Override
    @Transactional
    public PlaceRequest getPlaceById(UUID placeId) {
        PlaceEntity placeEntity = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException(messageService.getMessage("place.not.found")));
        return buildPlaceRequest(placeEntity);
    }

    @Override
    @Transactional
    public List<PlaceRequest> getRecommendedPlaces() {
        try {
            return placeRepository.findAllByRecommendedTrue().stream()
                    .map(this::buildPlaceRequest)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new PlaceApiException(messageService.getMessage("place.getting.all.error"), e);
        }
    }

    // ---------- search / filter ----------
    @Override
    @Transactional
    public List<PlaceRequest> searchPlaces(
            UUID placeTypeId, UUID countryId, UUID roadQualityId,
            UUID nearbyHotelsId, UUID nearbyHospitalsId, UUID nearbyPharmaciesId,
            UUID gasStationsId, UUID foodPlacesId, UUID climateZoneId) {

        Specification<PlaceEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (placeTypeId != null) predicates.add(cb.equal(root.get("placeTypeId"), placeTypeId));
            if (countryId != null) predicates.add(cb.equal(root.get("countryId"), countryId));
            if (roadQualityId != null) predicates.add(cb.equal(root.get("roadQualityId"), roadQualityId));
            if (climateZoneId != null) predicates.add(cb.equal(root.get("climateZoneId"), climateZoneId));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Set<UUID> idsFilter = null;
        if (nearbyHotelsId != null)
            idsFilter = intersect(idsFilter, placeNearbyHotelRepository.findPlaceIdsByHotelId(nearbyHotelsId));
        if (nearbyHospitalsId != null)
            idsFilter = intersect(idsFilter, placeNearbyHospitalRepository.findPlaceIdsByHospitalId(nearbyHospitalsId));
        if (nearbyPharmaciesId != null)
            idsFilter = intersect(idsFilter, placeNearbyPharmacyRepository.findPlaceIdsByPharmacyId(nearbyPharmaciesId));
        if (gasStationsId != null)
            idsFilter = intersect(idsFilter, placeGasStationRepository.findPlaceIdsByGasStationId(gasStationsId));
        if (foodPlacesId != null)
            idsFilter = intersect(idsFilter, placeFoodPlaceRepository.findPlaceIdsByFoodPlaceId(foodPlacesId));

        List<PlaceEntity> places = placeRepository.findAll(spec);
        if (idsFilter != null) {
            final Set<UUID> finalIds = idsFilter;
            places = places.stream().filter(p -> finalIds.contains(p.getId())).collect(Collectors.toList());
        }
        return places.stream().map(this::buildPlaceRequest).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private Set<UUID> intersect(Set<UUID> current, List<UUID> next) {
        Set<UUID> ns = new HashSet<>(next);
        if (current == null) return ns;
        current.retainAll(ns);
        return current;
    }

    @Override
    @Transactional
    public List<PlaceRequest> searchPlacesByKeywords(List<String> keywordValues) {
        if (keywordValues == null || keywordValues.isEmpty()) return Collections.emptyList();
        try {
            List<String> lower = keywordValues.stream().map(String::toLowerCase).collect(Collectors.toList());
            List<Keywords> matching = keywordRepository.findByNameInIgnoreCase(lower);
            if (matching.isEmpty()) return Collections.emptyList();

            List<UUID> kwIds = matching.stream().map(Keywords::getId).collect(Collectors.toList());
            List<UUID> placeIds = placeKeywordRepository.findPlaceIdsByAllKeywords(kwIds, kwIds.size());
            if (placeIds.isEmpty()) return Collections.emptyList();

            return placeRepository.findAllById(placeIds).stream()
                    .map(this::buildPlaceRequest).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (Exception e) {
            throw new PlaceApiException(messageService.getMessage("place.getting.all.error"), e);
        }
    }

    // ---------- create ----------
    @Override
    @Transactional
    public PlaceRequest createPlace(PlaceRequest request) {
        if (request.getId() != null) {
            throw new PlaceBadRequestException(messageService.getMessage("place.id.must.null"));
        }
        validateDuplicate(null, request.getName());

        PlaceEntity place = new PlaceEntity(request);
        applyJunctions(place, request);

        try {
            place = placeRepository.save(place);
        } catch (Exception e) {
            throw new PlaceApiException(messageService.getMessage("place.creating.error"), e);
        }
        return buildPlaceRequest(place);
    }

    // ---------- update ----------
    @Override
    @Transactional
    public PlaceRequest updatePlace(UUID id, PlaceRequest request) {
        PlaceEntity place = placeRepository.findById(id)
                .orElseThrow(() -> new PlaceNotFoundException(messageService.getMessage("place.not.found")));

        place.setName(request.getName());
        place.setCoordinates(request.getCoordinates());
        place.setDescription(request.getDescription());
        place.setRecommended(request.getRecommended());
        place.setPlaceTypeId(request.getPlaceTypeId());
        place.setCountryId(request.getCountryId());
        place.setClimateZoneId(request.getClimateZoneId());
        place.setPhoto360Id(request.getPhoto360Id());
        place.setRoadQualityId(request.getRoadQualityId());
        place.setTransportTypeId(request.getTransportTypeId());
        place.setPlaceRegionId(request.getPlaceRegionId());

        place.getPlaceNearbyHotels().clear();
        place.getPlaceNearbyHospitals().clear();
        place.getPlaceNearbyPharmacies().clear();
        place.getPlaceGasStations().clear();
        place.getPlaceFoodPlaces().clear();
        place.getPlacePhotos().clear();
        applyJunctions(place, request);

        try {
            return buildPlaceRequest(placeRepository.save(place));
        } catch (Exception e) {
            throw new PlaceApiException(messageService.getMessage("place.updating.error"), e);
        }
    }

    // ---------- delete ----------
    @Override
    @Transactional
    public void deletePlace(UUID id) {
        placeRepository.findById(id).orElseThrow(() ->
                new PlaceNotFoundException(messageService.getMessage("place.not.found")));
        try {
            placeRepository.deleteById(id);
        } catch (Exception e) {
            throw new PlaceApiException(messageService.getMessage("place.deleting.error"), e);
        }
    }

    // ---------- helpers ----------
    private void applyJunctions(PlaceEntity place, PlaceRequest req) {

        if (req.getNearbyHotels() != null) {
            for (PlaceNearbyHotelRequest r : req.getNearbyHotels()) {
                if (r == null || r.getHotelId() == null) continue;
                NearbyHotels hotel = nearbyHotelsRepository.findById(r.getHotelId()).orElse(null);
                if (hotel == null) continue;
                place.getPlaceNearbyHotels().add(PlaceNearbyHotel.builder()
                        .place(place).hotel(hotel).distance(r.getDistance()).build());
            }
        }
        if (req.getNearbyHospitals() != null) {
            for (PlaceNearbyHospitalRequest r : req.getNearbyHospitals()) {
                if (r == null || r.getHospitalId() == null) continue;
                NearbyHospitals h = nearbyHospitalsRepository.findById(r.getHospitalId()).orElse(null);
                if (h == null) continue;
                place.getPlaceNearbyHospitals().add(PlaceNearbyHospital.builder()
                        .place(place).hospital(h).distance(r.getDistance()).build());
            }
        }
        if (req.getNearbyPharmacies() != null) {
            for (PlaceNearbyPharmacyRequest r : req.getNearbyPharmacies()) {
                if (r == null || r.getPharmacyId() == null) continue;
                NearbyPharmacy ph = nearbyPharmaciesRepository.findById(r.getPharmacyId()).orElse(null);
                if (ph == null) continue;
                place.getPlaceNearbyPharmacies().add(PlaceNearbyPharmacy.builder()
                        .place(place).pharmacy(ph).distance(r.getDistance()).build());
            }
        }
        if (req.getGasStations() != null) {
            for (PlaceGasStationRequest r : req.getGasStations()) {
                if (r == null || r.getGasStationId() == null) continue;
                GasStation g = gasStationsRepository.findById(r.getGasStationId()).orElse(null);
                if (g == null) continue;
                place.getPlaceGasStations().add(PlaceGasStation.builder()
                        .place(place).gasStation(g).distance(r.getDistance()).build());
            }
        }
        if (req.getFoodPlaces() != null) {
            for (PlaceFoodPlaceRequest r : req.getFoodPlaces()) {
                if (r == null || r.getFoodPlaceId() == null) continue;
                FoodPlaces f = foodPlacesRepository.findById(r.getFoodPlaceId()).orElse(null);
                if (f == null) continue;
                place.getPlaceFoodPlaces().add(PlaceFoodPlace.builder()
                        .place(place).foodPlace(f).distance(r.getDistance()).build());
            }
        }
        if (req.getPhotoIds() != null) {
            for (UUID photoId : req.getPhotoIds()) {
                if (photoId == null) continue;
                Photo photo = photoRepository.findById(photoId).orElse(null);
                if (photo == null) continue;
                place.getPlacePhotos().add(PlacePhoto.builder()
                        .place(place).photo(photo).build());
            }
        }
    }

    private PlaceRequest buildPlaceRequest(PlaceEntity place) {
        if (place == null) return null;

        PlaceRequest req = place.toPlaceRequest();

        req.setNearbyHotels(place.getPlaceNearbyHotels().stream()
                .map(j -> PlaceNearbyHotelRequest.builder()
                        .hotelId(j.getHotel().getId()).distance(j.getDistance()).build())
                .collect(Collectors.toList()));

        req.setNearbyHospitals(place.getPlaceNearbyHospitals().stream()
                .map(j -> PlaceNearbyHospitalRequest.builder()
                        .hospitalId(j.getHospital().getId()).distance(j.getDistance()).build())
                .collect(Collectors.toList()));

        req.setNearbyPharmacies(place.getPlaceNearbyPharmacies().stream()
                .map(j -> PlaceNearbyPharmacyRequest.builder()
                        .pharmacyId(j.getPharmacy().getId()).distance(j.getDistance()).build())
                .collect(Collectors.toList()));

        req.setGasStations(place.getPlaceGasStations().stream()
                .map(j -> PlaceGasStationRequest.builder()
                        .gasStationId(j.getGasStation().getId()).distance(j.getDistance()).build())
                .collect(Collectors.toList()));

        req.setFoodPlaces(place.getPlaceFoodPlaces().stream()
                .map(j -> PlaceFoodPlaceRequest.builder()
                        .foodPlaceId(j.getFoodPlace().getId()).distance(j.getDistance()).build())
                .collect(Collectors.toList()));

        req.setPhotoIds(place.getPlacePhotos().stream()
                .map(j -> j.getPhoto().getId())
                .collect(Collectors.toList()));

        return req;
    }

    private void validateDuplicate(UUID id, String name) {
        Optional<PlaceEntity> existing = placeRepository.findByName(name);
        if (existing.isPresent()) {
            PlaceEntity placeEntity = existing.get();
            if (!placeEntity.getId().equals(id)) {
                throw new ClimateZoneAlreadyExistException(messageService.getMessage("place.already.exists"));
            }
        }
    }
}