package com.registration.repository;

import com.registration.entity.placefinder.GasStation;
import com.registration.entity.placefinder.NearbyHotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GasStationsRepository extends JpaRepository<GasStation, UUID> {
    @Query("SELECT nh.id FROM GasStation nh WHERE nh.name = :name")
    UUID findIdByName(@Param("name") String name);
    Optional<GasStation> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT nh FROM GasStation nh WHERE nh.regionId = :regionId")
    List<GasStation> findByRegionId(@Param("regionId") UUID regionId);
}
