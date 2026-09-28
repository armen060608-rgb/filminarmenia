package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.helper.LanguageUtil;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.NearbyPharmaciesRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.NEARBY_PHARMACIES_TABLE_NAME)
public class NearbyPharmacy {

    @Id
    @Column(name = "nearby_pharmacies_id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;
    private String name;
    @Column(name = "region_id")
    private UUID regionId;
    private String description;
    @Column(name = "description_hy")
    private String descriptionHy;
    @Column(name = "description_ru")
    private String descriptionRu;
    @Column(name = "name_hy")
    private String nameHy;
    @Column(name = "name_ru")
    private String nameRu;
    @Column(name = "photo_id", columnDefinition = "uuid")
    private UUID photoId;

    public NearbyPharmacy(NearbyPharmaciesRequest request) {
        id = request.getId();
        name = request.getName();
        regionId = request.getRegionId();
        description = request.getDescription();
        descriptionHy = request.getDescriptionHy();
        descriptionRu = request.getDescriptionRu();
        nameHy = request.getNameHy();
        nameRu = request.getNameRu();
        photoId = request.getPhotoId();
    }

    public NearbyPharmaciesRequest toNearbyPharmaciesRequest() {
        String localizedName = LanguageUtil.getLocalizedValue(name, nameRu, nameHy);
        String localizedDescription = LanguageUtil.getLocalizedValue(description, descriptionRu, descriptionHy);
        return new NearbyPharmaciesRequest(id, localizedName, regionId, localizedDescription, null, null, null, null, photoId);
    }

    public NearbyPharmaciesRequest toNearbyPharmaciesRequestFull() {
        return new NearbyPharmaciesRequest(id, name, regionId, description, descriptionHy, descriptionRu, nameHy, nameRu, photoId);
    }
}