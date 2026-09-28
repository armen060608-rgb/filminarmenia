package com.registration.repository;

import com.registration.entity.placefinder.PlaceGasStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceGasStationRepository extends JpaRepository<PlaceGasStation, UUID> {

    List<PlaceGasStation> findByPlaceId(UUID placeId);

    @Query("SELECT DISTINCT p.place.id FROM PlaceGasStation p WHERE p.gasStation.id = :gasStationId")
    List<UUID> findPlaceIdsByGasStationId(@Param("gasStationId") UUID gasStationId);
}
