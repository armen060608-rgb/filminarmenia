package com.registration.repository;

import com.registration.entity.placefinder.FoodPlaces;
import com.registration.entity.placefinder.NearbyHotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodPlacesRepository extends JpaRepository<FoodPlaces, UUID> {
    @Query("SELECT fp.id FROM FoodPlaces fp WHERE fp.name = :name")
    UUID findIdByName(@Param("name") String name);

    Optional<FoodPlaces> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT nh FROM FoodPlaces nh WHERE nh.regionId = :regionId")
    List<FoodPlaces> findByRegionId(@Param("regionId") UUID regionId);
}
