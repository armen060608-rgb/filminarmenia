package com.registration.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NearbyHospitalsRequest {

    @Hidden
    private UUID id;
    private String name;
    private UUID regionId;
    private String description;
    private String descriptionHy;
    private String descriptionRu;
    private String nameHy;
    private String nameRu;
    private UUID photoId;
}
