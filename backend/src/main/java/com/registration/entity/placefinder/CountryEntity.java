package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.helper.LanguageUtil;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.CountryRequest;
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
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.COUNTRY_TABLE_NAME)
public class CountryEntity {

    @Id
    @Column(name = "country_id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    private String name;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_hy")
    private String nameHy;

    public CountryEntity(CountryRequest request) {
        id = request.getId();
        name = request.getName();
        nameRu = request.getNameRu();
        nameHy = request.getNameHy();
    }

    public CountryRequest toCountryRequest() {
        String localizedName = LanguageUtil.getLocalizedValue(name, nameRu, nameHy);
        return new CountryRequest(id, localizedName, null, null);
    }

    public CountryRequest toCountryRequestFull() {
        return new CountryRequest(id, name, nameRu, nameHy);
    }
}