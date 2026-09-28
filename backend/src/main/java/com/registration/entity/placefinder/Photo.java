package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.helper.LanguageUtil;
import com.registration.request.ClimateZoneRequest;
import com.registration.request.PhotoRequest;
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
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.PHOTOS_TABLE_NAME)
public class Photo {

    @Id
    @Column(name = "photo_id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;
    private String name;
    private String size;
    private String path;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_hy")
    private String nameHy;

    private String description;

    @Column(name = "description_ru")
    private String descriptionRu;

    @Column(name = "description_hy")
    private String descriptionHy;

    public Photo(PhotoRequest request) {
        id = request.getId();
        name = request.getName();
        size = request.getSize();
        path = request.getPath();
        nameRu = request.getNameRu();
        nameHy = request.getNameHy();
        description = request.getDescription();
        descriptionRu = request.getDescriptionRu();
        descriptionHy = request.getDescriptionHy();
    }

    public PhotoRequest toPhotoRequest() {
        String localizedName = LanguageUtil.getLocalizedValue(name, nameRu, nameHy);
        String localizedDescription = LanguageUtil.getLocalizedValue(description, descriptionRu, descriptionHy);
        return new PhotoRequest(id, localizedName, size, path, null, null, localizedDescription, null, null);
    }


    public PhotoRequest toPhotoRequestFull() {
        return new PhotoRequest(id, name, size, path, nameRu, nameHy, description, descriptionRu, descriptionHy);
    }
}