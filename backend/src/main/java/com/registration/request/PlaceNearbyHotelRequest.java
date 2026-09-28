package com.registration.request;

import lombok.*;
import java.util.UUID;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class PlaceNearbyHotelRequest {
    private UUID hotelId;
    private Double distance;
}
