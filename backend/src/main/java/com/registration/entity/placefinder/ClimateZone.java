package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.helper.LanguageUtil;
import com.registration.request.ClimateZoneRequest;
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
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.CLIMATE_ZONE_TABLE_NAME)
public class ClimateZone {

    @Id
    @Column(name = "climate_zone_id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    private String name;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_hy")
    private String nameHy;

    public ClimateZone(ClimateZoneRequest request) {
        id = request.getId();
        name = request.getName();
        nameRu = request.getNameRu();
        nameHy = request.getNameHy();
    }

    public ClimateZoneRequest toClimateZoneRequest() {
        String localizedName = LanguageUtil.getLocalizedValue(name, nameRu, nameHy);
        return new ClimateZoneRequest(id, localizedName, null, null);
    }

    public ClimateZoneRequest toClimateZoneRequestFull() {
        return new ClimateZoneRequest(id, name, nameRu, nameHy);
    }
}