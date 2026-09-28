package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.helper.LanguageUtil;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.RegionRequest;
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
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.REGION_TABLE_NAME)
public class RegionEntity {

    @Id
    @Column(name = "region_id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    private String name;

    @Column(name = "country_id")
    private UUID countryId;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_hy")
    private String nameHy;

    public RegionEntity(RegionRequest request) {
        id = request.getId();
        name = request.getName();
        countryId = request.getCountryId();
        nameRu = request.getNameRu();
        nameHy = request.getNameHy();
    }

    public RegionRequest toRegionRequest() {
        String localizedName = LanguageUtil.getLocalizedValue(name, nameRu, nameHy);
        return new RegionRequest(id, localizedName, countryId, null, null);
    }

    public RegionRequest toRegionRequestFull() {
        return new RegionRequest(id, name, countryId, nameRu, nameHy);
    }
}