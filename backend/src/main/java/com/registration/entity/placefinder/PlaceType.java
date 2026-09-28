package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.helper.LanguageUtil;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.PlaceTypeRequest;
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
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.PLACE_TYPE_TABLE_NAME)
public class PlaceType {

    @Id
    @Column(name = "place_type_id")
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

    public PlaceType(PlaceTypeRequest request) {
        id = request.getId();
        name = request.getName();
        nameRu = request.getNameRu();
        nameHy = request.getNameHy();
        description = request.getDescription();
        descriptionRu = request.getDescriptionRu();
        descriptionHy = request.getDescriptionHy();
    }

    public PlaceTypeRequest toPlaceTypeRequest() {
        String localizedName = LanguageUtil.getLocalizedValue(name, nameRu, nameHy);
        String localizedDescription = LanguageUtil.getLocalizedValue(description, descriptionRu, descriptionHy);
        return new PlaceTypeRequest(id, localizedName, null, null, localizedDescription, null, null);
    }

    public PlaceTypeRequest toPlaceTypeRequestFull() {
        return new PlaceTypeRequest(id, name, nameRu, nameHy, description, descriptionRu, descriptionHy);
    }
}