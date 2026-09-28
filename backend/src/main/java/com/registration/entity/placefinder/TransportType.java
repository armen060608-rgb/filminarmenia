package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.helper.LanguageUtil;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.TransportTypeRequest;
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
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.TRANSPORT_TYPE_TABLE_NAME)
public class TransportType {

    @Id
    @Column(name = "transport_type_id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    private String name;
    private String nameRu;
    private String nameHy;
    private String description;
    private String descriptionRu;
    private String descriptionHy;

    public TransportType(TransportTypeRequest request) {
        id = request.getId();
        name = request.getName();
        nameRu = request.getNameRu();
        nameHy = request.getNameHy();
        description = request.getDescription();
        descriptionRu = request.getDescriptionRu();
        descriptionHy = request.getDescriptionHy();
    }

    public TransportTypeRequest toTransportTypeRequest() {
        String localizedName = LanguageUtil.getLocalizedValue(name, nameRu, nameHy);
        String localizedDescription = LanguageUtil.getLocalizedValue(description, descriptionRu, descriptionHy);
        return new TransportTypeRequest(id, localizedName, null, null, localizedDescription, null, null);
    }

    public TransportTypeRequest toTransportTypeRequestFull() {
        return new TransportTypeRequest(id, name, nameRu, nameHy, description, descriptionRu, descriptionHy);
    }
}