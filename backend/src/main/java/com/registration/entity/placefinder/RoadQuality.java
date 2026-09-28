package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.helper.LanguageUtil;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.RoadQualityRequest;
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
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.ROAD_QUALITY_TABLE_NAME)
public class RoadQuality {

    @Id
    @Column(name = "road_quality_id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    private String name;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_hy")
    private String nameHy;

    private String description;

    @Column(name = "description_ru")
    private String descriptionRu;

    @Column(name = "description_hy")
    private String descriptionHy;

    public RoadQuality(RoadQualityRequest request) {
        id = request.getId();
        name = request.getName();
        nameRu = request.getNameRu();
        nameHy = request.getNameHy();
        description = request.getDescription();
        descriptionRu = request.getDescriptionRu();
        descriptionHy = request.getDescriptionHy();
    }

    public RoadQualityRequest toRoadQualityRequest() {
        String localizedName = LanguageUtil.getLocalizedValue(name, nameRu, nameHy);
        String localizedDescription = LanguageUtil.getLocalizedValue(description, descriptionRu, descriptionHy);
        return new RoadQualityRequest(id, localizedName, null, null, localizedDescription, null, null);
    }

    public RoadQualityRequest toRoadQualityRequestFull() {
        return new RoadQualityRequest(id, name, nameRu, nameHy, description, descriptionRu, descriptionHy);
    }
}