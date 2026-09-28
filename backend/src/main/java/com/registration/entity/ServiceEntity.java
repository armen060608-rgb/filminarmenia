package com.registration.entity;

import com.registration.constants.DatabaseConstants;
import com.registration.helper.LanguageUtil;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.ServiceRequest;
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
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.SERVICE_TABLE_NAME)
public class ServiceEntity {

    @Id
    @Column(name = "service_id")
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

    @Column(name = "service_type_id", columnDefinition = "uuid")
    private UUID serviceTypeId;

    public ServiceEntity(ServiceRequest request) {
        id = request.getId();
        name = request.getName();
        nameRu = request.getNameRu();
        nameHy = request.getNameHy();
        description = request.getDescription();
        descriptionRu = request.getDescriptionRu();
        descriptionHy = request.getDescriptionHy();
        serviceTypeId = request.getServiceTypeId();
    }

    public ServiceRequest toServiceRequest() {
        String localizedName = LanguageUtil.getLocalizedValue(name, nameRu, nameHy);
        String localizedDescription = LanguageUtil.getLocalizedValue(description, descriptionRu, descriptionHy);
        return new ServiceRequest(id, localizedName, null, null, localizedDescription, null, null, serviceTypeId);
    }

    public ServiceRequest toServiceRequestFull() {
        return new ServiceRequest(id, name, nameRu, nameHy, description, descriptionRu, descriptionHy, serviceTypeId);
    }
}