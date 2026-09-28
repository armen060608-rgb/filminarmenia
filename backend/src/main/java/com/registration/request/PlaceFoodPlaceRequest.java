package com.registration.request;

import lombok.*;
import java.util.UUID;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class PlaceFoodPlaceRequest {
    private UUID foodPlaceId;
    private Double distance;
}
