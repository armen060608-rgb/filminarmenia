package com.registration.request;

import lombok.*;
import java.util.UUID;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class PlaceGasStationRequest {
    private UUID gasStationId;
    private Double distance;
}
