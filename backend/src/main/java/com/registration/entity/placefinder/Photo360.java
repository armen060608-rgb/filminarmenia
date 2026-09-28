package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.helper.LanguageUtil;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.Photo360Request;
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
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.PHOTO_360_TABLE_NAME)
public class Photo360 {

    @Id
    @Column(name = "photo_360_id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;
    private String name;
    private String size;
    private String path;
    private String description;
    private String nameRu;
    private String nameHy;
    private String descriptionRu;
    private String descriptionHy;

    public Photo360(Photo360Request request) {
        id = request.getId();
        name = request.getName();
        size = request.getSize();
        path = request.getPath();
        description = request.getDescription();
        nameRu = request.getNameRu();
        nameHy = request.getNameHy();
        descriptionRu = request.getDescriptionRu();
        descriptionHy = request.getDescriptionHy();
    }

    public Photo360Request toPhoto360Request() {
        String localizedName = LanguageUtil.getLocalizedValue(name, nameRu, nameHy);
        String localizedDescription = LanguageUtil.getLocalizedValue(description, descriptionRu, descriptionHy);
        return new Photo360Request(id, localizedName, size, path, localizedDescription, null, null, null, null);
    }

    public Photo360Request toPhoto360RequestFull() {
        return new Photo360Request(id, name, size, path, description, nameRu, nameHy, descriptionRu, descriptionHy);
    }
}