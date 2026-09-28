package com.registration.request;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoRequest {

    @Hidden
    private UUID id;

    private String name;

    private String size;

    private String path;

    private String nameRu;

    private String nameHy;

    private String description;

    private String descriptionRu;

    private String descriptionHy;
}
