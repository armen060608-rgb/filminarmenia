package com.registration.request;

import lombok.*;
import java.util.UUID;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class PlaceNearbyPharmacyRequest {
    private UUID pharmacyId;
    private Double distance;
}
