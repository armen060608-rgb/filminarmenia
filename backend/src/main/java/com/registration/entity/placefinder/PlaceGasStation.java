package com.registration.entity.placefinder;

import com.registration.constants.DatabaseConstants;
import com.registration.entity.PlaceEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(schema = DatabaseConstants.SCHEMA_NAME, name = DatabaseConstants.PLACE_GAS_STATION_TABLE_NAME)
public class PlaceGasStation {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private PlaceEntity place;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gas_station_id", nullable = false)
    private GasStation gasStation;

    @Column(name = "distance")
    private Double distance;
}
