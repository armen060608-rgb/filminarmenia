package com.registration.repository;

import com.registration.entity.placefinder.NearbyHospitals;
import com.registration.entity.placefinder.NearbyHotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NearbyHospitalsRepository extends JpaRepository<NearbyHospitals, UUID> {
    @Query("SELECT nh.id FROM NearbyHospitals nh WHERE nh.name = :name")
    UUID findIdByName(@Param("name") String name);
    Optional<NearbyHospitals> findByName(String name);

    boolean existsByName(String name);


    @Query("SELECT nh FROM NearbyHospitals nh WHERE nh.regionId = :regionId")
    List<NearbyHospitals> findByRegionId(@Param("regionId") UUID regionId);
}
