package com.registration.entity;

import com.registration.constants.DatabaseConstants;
import com.registration.entity.placefinder.*;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.PlaceRequest;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.PLACE_TABLE_NAME)
public class PlaceEntity {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_hy")
    private String nameHy;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "coordinates")
    private String coordinates;

    @Column(name = "place_type_id")
    private UUID placeTypeId;

    @Column(name = "country_id")
    private UUID countryId;

    @Column(name = "climate_zone_id")
    private UUID climateZoneId;

    @Column(name = "photo_360_id")
    private UUID photo360Id;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "description_hy")
    private String descriptionHy;

    @Column(name = "description_ru")
    private String descriptionRu;

    @Column(name = "road_quality_id")
    private UUID roadQualityId;

    @Column(name = "recommended")
    private Boolean recommended;

    @Column(name = "transport_type_id", columnDefinition = "uuid[]")
    @Type(ListArrayType.class)
    private List<UUID> transportTypeId;

    @Column(name = "region_id")
    private UUID placeRegionId;

    // ---- NEW: many-to-many junctions (the same style as placeKeywords) ----

    @Builder.Default
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceKeyword> placeKeywords = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceNearbyHotel> placeNearbyHotels = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceNearbyHospital> placeNearbyHospitals = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceNearbyPharmacy> placeNearbyPharmacies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceGasStation> placeGasStations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceFoodPlace> placeFoodPlaces = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlacePhoto> placePhotos = new ArrayList<>();

    public PlaceEntity(PlaceRequest request) {
        this.id = request.getId();
        this.name = request.getName();
        this.nameHy = request.getNameHy();
        this.nameRu = request.getNameRu();
        this.coordinates = request.getCoordinates();
        this.description = request.getDescription();
        this.descriptionHy = request.getDescriptionHy();
        this.descriptionRu = request.getDescriptionRu();
        this.recommended = request.getRecommended();
        this.placeTypeId = request.getPlaceTypeId();
        this.countryId = request.getCountryId();
        this.climateZoneId = request.getClimateZoneId();
        this.photo360Id = request.getPhoto360Id();
        this.roadQualityId = request.getRoadQualityId();
        this.transportTypeId = request.getTransportTypeId();
        this.placeRegionId = request.getPlaceRegionId();
    }

    public PlaceRequest toPlaceRequest() {
        return PlaceRequest.builder()
                .id(id)
                .name(name)
                .nameHy(nameHy)
                .nameRu(nameRu)
                .coordinates(coordinates)
                .description(description)
                .descriptionHy(descriptionHy)
                .descriptionRu(descriptionRu)
                .recommended(recommended)
                .placeTypeId(placeTypeId)
                .countryId(countryId)
                .climateZoneId(climateZoneId)
                .photo360Id(photo360Id)
                .roadQualityId(roadQualityId)
                .transportTypeId(transportTypeId)
                .placeRegionId(placeRegionId)
                .build();
    }
}
