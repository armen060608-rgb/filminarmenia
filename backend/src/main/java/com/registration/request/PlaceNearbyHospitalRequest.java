package com.registration.request;

import lombok.*;
import java.util.UUID;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class PlaceNearbyHospitalRequest {
    private UUID hospitalId;
    private Double distance;
}
