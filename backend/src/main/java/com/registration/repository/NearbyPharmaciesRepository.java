package com.registration.repository;

import com.registration.entity.placefinder.NearbyHotels;
import com.registration.entity.placefinder.NearbyPharmacy;
import com.registration.entity.placefinder.PlaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NearbyPharmaciesRepository extends JpaRepository<NearbyPharmacy, UUID> {
    @Query("SELECT nh.id FROM NearbyPharmacy nh WHERE nh.name = :name")
    UUID findIdByName(@Param("name") String name);
    String findNameById(UUID id);
    Optional<NearbyPharmacy> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT nh FROM NearbyPharmacy nh WHERE nh.regionId = :regionId")
    List<NearbyPharmacy> findByRegionId(@Param("regionId") UUID regionId);
}
