package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.helper.LanguageUtil;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.NearbyHotelsRequest;
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
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.NEARBY_HOTELS_TABLE_NAME)
public class NearbyHotels {

    @Id
    @Column(name = "nearby_hotels_id", columnDefinition = "uuid")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_hy")
    private String nameHy;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "rate")
    private String rate;

    @Column(name = "website")
    private String website;

    @Column(name = "region_id", columnDefinition = "uuid")
    private UUID regionId;

    @Column(name = "description")
    private String description;

    @Column(name = "description_hy")
    private String descriptionHy;

    @Column(name = "description_ru")
    private String descriptionRu;

    @Column(name = "photo_id", columnDefinition = "uuid")
    private UUID photoId;

    public NearbyHotelsRequest toNearbyHotelsRequest() {
        String localizedName = LanguageUtil.getLocalizedValue(name, nameRu, nameHy);
        String localizedDescription = LanguageUtil.getLocalizedValue(description, descriptionRu, descriptionHy);

        return NearbyHotelsRequest.builder()
                .id(this.id)
                .name(localizedName)
                .nameHy(this.nameHy)
                .nameRu(this.nameRu)
                .rate(this.rate)
                .website(this.website)
                .regionId(this.regionId)
                .description(localizedDescription)
                .descriptionHy(this.descriptionHy)
                .descriptionRu(this.descriptionRu)
                .photoId(this.photoId)
                .build();
    }


    public NearbyHotelsRequest toNearbyHotelsRequestFull() {
        return new NearbyHotelsRequest(id, name, nameHy, nameRu, rate, website, regionId, description, descriptionHy, descriptionRu, photoId);
    }
}