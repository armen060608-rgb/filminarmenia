package com.registration.repository;

import com.registration.entity.placefinder.PlaceNearbyHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceNearbyHospitalRepository extends JpaRepository<PlaceNearbyHospital, UUID> {

    List<PlaceNearbyHospital> findByPlaceId(UUID placeId);

    @Query("SELECT DISTINCT p.place.id FROM PlaceNearbyHospital p WHERE p.hospital.id = :hospitalId")
    List<UUID> findPlaceIdsByHospitalId(@Param("hospitalId") UUID hospitalId);
}
