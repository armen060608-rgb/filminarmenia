package com.registration.repository;

import com.registration.entity.placefinder.PlaceNearbyPharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceNearbyPharmacyRepository extends JpaRepository<PlaceNearbyPharmacy, UUID> {

    List<PlaceNearbyPharmacy> findByPlaceId(UUID placeId);

    @Query("SELECT DISTINCT p.place.id FROM PlaceNearbyPharmacy p WHERE p.pharmacy.id = :pharmacyId")
    List<UUID> findPlaceIdsByPharmacyId(@Param("pharmacyId") UUID pharmacyId);
}
