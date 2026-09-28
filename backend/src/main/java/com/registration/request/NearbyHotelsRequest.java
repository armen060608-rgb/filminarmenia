package com.registration.request;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NearbyHotelsRequest {

    @Hidden
    private UUID id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "NameHy cannot be empty")
    private String nameHy;

    @NotBlank(message = "NameRu cannot be empty")
    private String nameRu;

    @NotNull(message = "Rate cannot be null")
    @Positive(message = "Rate must be positive")
    private String rate;

    @NotBlank(message = "Website cannot be empty")
    private String website;

    @NotNull(message = "RegionId cannot be null")
    private UUID regionId;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotBlank(message = "DescriptionHy cannot be empty")
    private String descriptionHy;

    @NotBlank(message = "DescriptionRu cannot be empty")
    private String descriptionRu;

    @NotNull(message = "PhotoId cannot be null")
    private UUID photoId;
}