package com.registration.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceRequest {

    @Hidden
    private UUID id;

    private String name;
    private String nameHy;
    private String nameRu;
    private String coordinates;

    private UUID placeTypeId;
    private UUID countryId;
    private UUID climateZoneId;
    private UUID photo360Id;

    private String description;
    private String descriptionHy;
    private String descriptionRu;
    private UUID roadQualityId;
    private List<UUID> transportTypeId;
    private Boolean recommended;
    private UUID placeRegionId;

    // ---- NEW: many-to-many lists with distance ----
    private List<PlaceNearbyHotelRequest>     nearbyHotels;
    private List<PlaceNearbyHospitalRequest>  nearbyHospitals;
    private List<PlaceNearbyPharmacyRequest>  nearbyPharmacies;
    private List<PlaceGasStationRequest>      gasStations;
    private List<PlaceFoodPlaceRequest>       foodPlaces;

    // ---- NEW: photos as list (instead of single photoId / uuid[] column) ----
    private List<UUID> photoIds;
}
